package com.holo.chatmode.gui;

import java.io.IOException;

import com.holo.chatmode.reference.Reference;
import com.holo.chatmode.util.HWID;

import net.minecraft.client.gui.*;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;

public class ModGui extends GuiScreen {

    private GuiButton mButtonClose;
    
    private GuiLabel hash;
    
    private GuiButton autoAnswerButton;
    private GuiButton antiAFKButton;
    private GuiButton sellFishButton;
    
    private GuiButton copyOnKayboard;
    
    private String on = "§2Включено";
    private String off = "§4Выключено";

    private boolean isCopy = false;
    
    @Override
    public void initGui() 
    {
        super.initGui();
        this.buttonList.add(mButtonClose = new GuiButton(0, this.width / 2 - 100, this.height - (this.height / 4) + 10, "Закрыть"));
        
        // Сменяемая кнопка
        if (Reference.autoAnswer) 
        {
    		this.buttonList.add(autoAnswerButton = new GuiButton(1, this.width / 2 - 100, this.height - (this.height / 4) - 50, "Ответы на примеры " + on));
    	}
        else 
    	{
    		this.buttonList.add(autoAnswerButton = new GuiButton(1, this.width / 2 - 100, this.height - (this.height / 4) - 50, "Ответы на примеры " + off));
    	}
        
        
        if (Reference.antiAFK) 
        {
    		this.buttonList.add(antiAFKButton = new GuiButton(1, this.width / 2 - 100, this.height - (this.height / 4) - 70, "Анти АФК " + on));
    	}
        else 
    	{
    		this.buttonList.add(antiAFKButton = new GuiButton(1, this.width / 2 - 100, this.height - (this.height / 4) - 70, "Анти АФК " + off));
    	}
        
        if (Reference.sellFish) 
        {
    		this.buttonList.add(sellFishButton = new GuiButton(1, this.width / 2 - 100, this.height - (this.height / 4) - 90, "Продажа рыбы " + on));
    	}
        else 
    	{
    		this.buttonList.add(sellFishButton = new GuiButton(1, this.width / 2 - 100, this.height - (this.height / 4) - 90, "Продажа рыбы " + off));
    	}

        this.buttonList.add(copyOnKayboard = new GuiButton(1, this.width / 2 - 100, this.height - (this.height / 4) - 110, "Скопировать код в буфер обмена"));
        
        this.labelList.add(hash = new GuiLabel(fontRenderer, 1, this.width / 2 - 100, this.height / 2 + 40, 300, 20, 0xFFFFFF));
        hash.addLine("Твой уникальй код: " + HWID.getHWID());
        
        if (!Reference.auth) {
        	hash.addLine("§4Ты не авторизирован!");
        } else {
        	hash.addLine("§2Ты авторизирован!");
        }
        
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button == mButtonClose) {
        	mc.player.closeScreen();
        } 
        else if (button == autoAnswerButton) 
        {
        	if (!Reference.autoAnswer) {
        		Reference.autoAnswer = true;
        		button.displayString = "Ответы на примеры " + on;
        	} else {
        		Reference.autoAnswer = false;
        		button.displayString = "Ответы на примеры " + off;
        	}
        }
        else if (button == antiAFKButton) 
        {
        	if (!Reference.antiAFK) {
        		Reference.antiAFK = true;
        		button.displayString = "Анти АФК " + on;
        	} else {
        		Reference.antiAFK = false;
        		button.displayString = "Анти АФК " + off;
        	}
        }
        else if (button == sellFishButton) 
        {
        	if (!Reference.sellFish) {
        		Reference.sellFish = true;
        		button.displayString = "Продажа рыбы " + on;
        	} else {
        		Reference.sellFish = false;
        		button.displayString = "Продажа рыбы " + off;
        	}
        }
        else if (button == copyOnKayboard) {
        	button.displayString = "§2Скопировать код в буфер обмена";
        	StringSelection stringSelection = new StringSelection(HWID.getHWID());
        	Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        	clipboard.setContents(stringSelection, null);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
