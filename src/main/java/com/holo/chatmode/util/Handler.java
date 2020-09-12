package com.holo.chatmode.util;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

import java.util.TimerTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.holo.chatmode.proxy.ClientProxy;

import java.util.Timer;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Handler {
	
	public static int countAnswer;
	public static boolean auth;
    public static String nsChest;
    public static String subTitle;
    public static boolean isPrinted;
    public static boolean isAuth;
	
	public static Double calc(List<String> postfix) {
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
	
	@SubscribeEvent
    public static void checkMessage(final ClientChatReceivedEvent event) {
        if (auth) {
            String msg = event.getMessage().getUnformattedText().trim();
            if (msg.startsWith("Решите пример:")) {
                msg = msg.substring(15);
                System.out.println("First" + (int)msg.charAt(1));
                System.out.println("Mes = " + msg);
                final String s = changeStr(msg);
                System.out.println("Second" + (int)s.charAt(1));
                System.out.println(s);
                Calculate n = new Calculate();
                List<String> expression = Calculate.parse(s);
                boolean flag = Calculate.flag;
                if (flag) {
                    for (final String x : expression) {
                        System.out.print(x + " ");
                    }
                    final double answer = calc(expression);
                    final int result = (int)answer;
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (countAnswer >= 3) {
                            	Minecraft.getMinecraft().player.sendMessage((ITextComponent)new TextComponentTranslation("Все сработоло, но не отправилось. Так должнооо быть =)"));
                                countAnswer = 0;
                            }
                            else {
                            	Minecraft.getMinecraft().player.sendChatMessage(Integer.toString(result));
                            	Minecraft.getMinecraft().player.sendMessage((ITextComponent)new TextComponentTranslation("Пример решен."));
                            }
                        }
                    }, 2501 + (int)(Math.random() * 199.0));
                }
            }
            else if (msg.startsWith("Успей забрать всё:")) {
            	nsChest = msg.substring(19).trim();
            }
            else if (msg.startsWith("Ты выловил")) {
            	Minecraft.getMinecraft().player.sendChatMessage("/sellfish");
            }
        }
    }
	
	@SideOnly(Side.CLIENT)
    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public static void onEvent(final InputEvent.KeyInputEvent event) {
        final KeyBinding keyBindings = ClientProxy.keyBindings;
        if (keyBindings.isPressed()) {
            if (auth) {
                if (nsChest == "") {
                    Minecraft.getMinecraft().player.sendMessage((ITextComponent)new TextComponentTranslation("nexuschest'a ��� �� ����!"));
                }
                else {
                	 Minecraft.getMinecraft().player.sendChatMessage(nsChest);
                }
            }
            else {
            	Minecraft.getMinecraft().player.sendMessage((ITextComponent)new TextComponentTranslation("���� ��� �� ���� =)"));
            }
        }
    }
	
	@SubscribeEvent
	public static void fish(WorldEvent.Load wd/* final TickEvent.PlayerTickEvent event */) {
        if (!isAuth) {
        	isAuth = true;
            String nickname = Minecraft.getMinecraft().player.getName().trim();
            System.out.println("http://www.prime-test.org/?hash=x010&name=" + nickname);
            try {
                final Document doc = Jsoup.connect("http://www.prime-test.org/?hash=x010&name=" + nickname).get();
                final Elements paragraphs = doc.select("p");
                for (final Element paragraph : paragraphs) {
                    System.out.println(paragraph.text());
                    if (paragraph.text().equals("ok")) {
                        auth = true;
                    }
                    else {
                        auth = false;
                    }
                }
            }
            catch (IOException e) {
                e.printStackTrace();
                auth = false;
            }
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                	isAuth = false;
                }
            }, 90000);
        }
    }
	
	public static String changeStr(final String msg) {
        String newMsg = "";
        final String[] sym = { "\uff10", "\uff11", "\uff12", "\uff13", "\uff14", "\uff15", "\uff16", "\uff17", "\uff18", "\uff19", "\uff0b", "\uff0d", "\uff0f", "\uff0a" };
        for (int i = 0; i < msg.length(); ++i) {
            System.out.println("Int i = " + i);
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
                    System.out.println("SimMesNew = " + msg);
                    ++countAnswer;
                    return msg;
                }
                newMsg += " ";
            }
        }
        System.out.println("new = " + newMsg);
        ++countAnswer;
        return newMsg;
    }
}
