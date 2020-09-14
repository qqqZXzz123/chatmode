package com.holo.chatmode.gui;

import java.io.IOException;

import com.holo.chatmode.reference.Reference;

import net.minecraft.client.gui.*;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

public class ModGui extends GuiScreen {

    private GuiButton mButtonClose;
    private GuiButton offMod;
    private GuiLabel mLabelIpAddress;
    private GuiTextField txt;

    @Override
    public void initGui() 
    {
        super.initGui();
        this.buttonList.add(mButtonClose = new GuiButton(0, this.width / 2 - 100, this.height - (this.height / 4) + 10, "Закрыть"));
        
        // Сменяемая кнопка
        if (Reference.auth) 
        {
    		this.buttonList.add(offMod = new GuiButton(0, this.width / 2 - 100, this.height - (this.height / 4) - 50, "Работает"));
    	}
        else 
    	{
    		this.buttonList.add(offMod = new GuiButton(0, this.width / 2 - 100, this.height - (this.height / 4) - 50, "Выключено"));
    	}
        
        this.labelList.add(mLabelIpAddress = new GuiLabel(fontRenderer, 1, this.width / 2 - 20, this.height / 2 + 40, 300, 20, 0xFFFFFF));

        mLabelIpAddress.addLine("ЕБАТЬ ОНО РАБОТАЕТ!?!?!??!");
        mLabelIpAddress.addLine("sadgafsgbaf");
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button == mButtonClose) {
        	mc.player.closeScreen();
        } 
        else if (button == offMod) 
        {
        	if (!Reference.auth) {
        		Reference.auth = true;
        		button.displayString = "Работает";
        	} else {
        		Reference.auth = false;
        		button.displayString = "Выключено";
        	}
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
