package com.holo.chatmode.util;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

import java.util.TimerTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.holo.chatmode.gui.ModGui;
import com.holo.chatmode.proxy.ClientProxy;
import com.holo.chatmode.reference.Reference;

import java.util.Timer;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Handler {
	
    public static int countAnswer = 2;
	
    public String nsChest = "";
    public String subTitle = "";
    
    public boolean isPrinted = false;
    public boolean isAntiAFK = false;
    public boolean isAuth = false;
	// Функция для подсчета в примерах (добросовестно спиздил у кого-то, потому что лень)
	public Double calc(List<String> postfix) {
		Deque<Double> stack = new ArrayDeque<Double>();
		for (String x : postfix) {
			if (x.equals("sqrt")) stack.push(Math.sqrt(stack.pop()));
			else if (x.equals("cube")) {
				Double tmp = stack.pop();
				stack.push(tmp * tmp * tmp);
			}
			else if (x.equals("pow10")) stack.push(Math.pow(10, stack.pop()));
			else if (x.equals("+")) stack.push(stack.pop() + stack.pop());
			else if (x.equals("-")) {
				Double b = stack.pop(), a = stack.pop();
				stack.push(a - b);
			}
			else if (x.equals("*")) stack.push(stack.pop() * stack.pop());
			else if (x.equals("/")) {
				Double b = stack.pop(), a = stack.pop();
				stack.push(a / b);
			}
			else if (x.equals("u-")) stack.push(-stack.pop());
			else stack.push(Double.valueOf(x));
		}
		return stack.pop();
	}
	// Проверка содержимого новых сообщений в чате
	@SubscribeEvent
    public void checkMessage(ClientChatReceivedEvent event) {
        if (Reference.auth) {
            String msg = event.getMessage().getUnformattedText().trim(); // получаем сообщение, убирая лишние пробелы
            if (msg.startsWith("Решите пример:")) { // Решаем пример
            	if (Reference.autoAnswer) {
	                msg = msg.substring(15);
	                final String s = changeStr(msg);
	                Calculate n = new Calculate();
	                List<String> expression = Calculate.parse(s);
	                boolean flag = Calculate.flag;
	                if (flag) {
	                    for (String x : expression) {
	                        //System.out.print(x + " ");
	                    }
	                    double answer = calc(expression);
	                    int result = (int)answer;
	                    new Timer().schedule(new TimerTask() { // Небольшая задержка, чтобы не сильно палиться, что ты решаешь не сам.
	                        @Override
	                        public void run() {
	                            if (countAnswer >= 2) {
	                            	Minecraft.getMinecraft().player.sendMessage((ITextComponent)new TextComponentTranslation("Все сработоло, но не отправилось. Так должнооо быть =)"));
	                                countAnswer = 0;
	                            }
	                            else {
	                            	Minecraft.getMinecraft().player.sendChatMessage(Integer.toString(result));
	                            	Minecraft.getMinecraft().player.sendMessage((ITextComponent)new TextComponentTranslation("Пример решен."));
	                            }
	                        }
	                    }, 2301 + (int)(Math.random() * 399.0)); // Та самая задержка
	                }
            	}
            }
            else if (msg.startsWith("Успей забрать всё:")) { // Для работы бинда
            	nsChest = msg.substring(19).trim();
            }
            else if (msg.startsWith("Ты выловил")) { // Продажа рыбки. Можно было сделать с помощью отдельного event'a
            	if (Reference.sellFish)
            		Minecraft.getMinecraft().player.sendChatMessage("/sellfish");
            }
        }
    }
	// Полечение и вписывание кода, при авторыбалке.
	@SuppressWarnings("null")
	@SubscribeEvent
    public void getCode(TickEvent.PlayerTickEvent event) {
		if (Reference.auth) {
	        if (!isPrinted) {
	            try {
			    // Получаем текст на дисплее
	                subTitle = (String) ReflectionHelper.findField(GuiIngame.class, "displayedSubTitle", "field_175200_y").get(Minecraft.getMinecraft().ingameGUI);
	            }
	            catch (IllegalArgumentException | IllegalAccessException ex2) {
	                Exception e = null;
	                e.printStackTrace();
	            }
	            if (subTitle.startsWith("§c\u041d\u0430\u043f\u0438\u0448\u0438\u0442\u0435")) { // Ну там сверяется с текстом на экране (например: мы подозреваем...). §c - цвет текста
	                isPrinted = true;
	                subTitle = subTitle.substring(17);
	                subTitle = subTitle.substring(0, 15);
	                Minecraft.getMinecraft().player.sendChatMessage(subTitle);
	                //System.out.println(subTitle);
	                subTitle = "";
	                new Timer().schedule(new TimerTask() { // задержка, чтобы не лагало.
	                    @Override
	                    public void run() {
	                    	isPrinted = false;
	                    }
	                }, 120000);
	            }
	        }
	    }
	}
	// Анти афк. Если вдруг снова появиться вот это "покрутите мышкой" - посмотри новый антиафк в nexushelper'e
	@SubscribeEvent
    public void antiAFK(TickEvent.PlayerTickEvent event) {
			if (Reference.auth) 
			{
		        if (Reference.antiAFK) 
		        {
		        	if (!isAntiAFK) 
		        	{
		        		isAntiAFK = true;
			        	Minecraft.getMinecraft().player.sendChatMessage("/near");
		                new Timer().schedule(new TimerTask() {
		                    @Override
		                    public void run() {
		                    	isAntiAFK = false;
		                    }
		                }, 200000);
		        }
		    }
		}
	}
	// Бинд на тп к НЧ. 
	@SideOnly(Side.CLIENT)
    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public void onEvent(InputEvent.KeyInputEvent event) {
        KeyBinding keyBindings = ClientProxy.keyBindingsNC;
        if (keyBindings.isPressed()) {
            if (Reference.auth) {
                if (nsChest == "") {
                    Minecraft.getMinecraft().player.sendMessage((ITextComponent)new TextComponentTranslation("nexuschest'a  еще не было!!"));
                }
                else {
                	 Minecraft.getMinecraft().player.sendChatMessage(nsChest);
                }
            }
            else {
            	Minecraft.getMinecraft().player.sendMessage((ITextComponent)new TextComponentTranslation("Вот купил бы - заработало сейчас. А тааак =((("));
            }
        }
    }
	// Открытие меню по нажатию
	@SideOnly(Side.CLIENT)
    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public void guiMenu(InputEvent.KeyInputEvent event) {
        KeyBinding keyBindings = ClientProxy.keyBindingsGui;
        if (keyBindings.isPressed()) {
            Minecraft.getMinecraft().displayGuiScreen(new ModGui());
        }
    }
	// Аунтификация в мод. 
	@SubscribeEvent
	public void fish(TickEvent.PlayerTickEvent event) {
        if (!isAuth) {
        	isAuth = true;
            //String nickname = Minecraft.getMinecraft().player.getName().trim();
            //System.out.println("http://url_hosta/?hash=x010&name=" + nickname);
            try {
                Document doc = Jsoup.connect("http://url_hosta/?hash=x010&name=" /*+ "0.4.1"*/ + HWID.getHWID()).get();
                Elements paragraphs = doc.select("p");
                for (Element paragraph : paragraphs) {
                    if (paragraph.text().equals("ok")) {
                    	Reference.auth = true;
                    }
                    else {
                    	Reference.auth = false;
                    }
                }
            }
            catch (IOException e) {
                e.printStackTrace();
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                    	Reference.auth = false;
                    }
                }, 300000);
            }
            
            // цыкл тиков
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                	isAuth = false;
                }
            }, 300000);
        }
    }
	// Получение цифр с примеров
	public String changeStr(String msg) {
        String newMsg = "";
        final String[] sym = { "\uff10", "\uff11", "\uff12", "\uff13", "\uff14", "\uff15", "\uff16", "\uff17", "\uff18", "\uff19", "\uff0b", "\uff0d", "\uff0f", "\uff0a" };
        for (int i = 0; i < msg.length(); ++i) {
            //System.out.println("Int i = " + i);
            if (msg.charAt(i) == sym[0].charAt(0)) {
                newMsg += "0";
            }
            else if (msg.charAt(i) == sym[1].charAt(0)) {
                newMsg += "1";
            }
            else if (msg.charAt(i) == sym[2].charAt(0)) {
                newMsg += "2";
            }
            else if (msg.charAt(i) == sym[3].charAt(0)) {
                newMsg += "3";
            }
            else if (msg.charAt(i) == sym[4].charAt(0)) {
                newMsg += "4";
            }
            else if (msg.charAt(i) == sym[5].charAt(0)) {
                newMsg += "5";
            }
            else if (msg.charAt(i) == sym[6].charAt(0)) {
                newMsg += "6";
            }
            else if (msg.charAt(i) == sym[7].charAt(0)) {
                newMsg += "7";
            }
            else if (msg.charAt(i) == sym[8].charAt(0)) {
                newMsg += "8";
            }
            else if (msg.charAt(i) == sym[9].charAt(0)) {
                newMsg += "9";
            }
            else if (msg.charAt(i) == sym[10].charAt(0)) {
                newMsg += "+";
            }
            else if (msg.charAt(i) == sym[11].charAt(0)) {
                newMsg += "-";
            }
            else if (msg.charAt(i) == sym[12].charAt(0)) {
                newMsg += "/";
            }
            else if (msg.charAt(i) == sym[13].charAt(0)) {
                newMsg += "*";
            }
            else {
                if (msg.charAt(i) != ' ') {
                    //System.out.println("SimMesNew = " + msg);
                    ++countAnswer;
                    return msg;
                }
                newMsg += " ";
            }
        }
        //System.out.println("new = " + newMsg);
        ++countAnswer;
        return newMsg;
    }
}
