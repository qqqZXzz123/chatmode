package com.holo.chatmode.gui;

import java.io.IOException;

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
    
    private String hButtonValue = "Выключено";
    
    private boolean aaaa = false;

    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.add(mButtonClose = new GuiButton(0, this.width / 2 - 100, this.height - (this.height / 4) + 10, "Закрыть"));
        if (aaaa)
        	this.buttonList.add(offMod = new GuiButton(0, this.width / 2 - 100, this.height - (this.height / 4) - 50, "Работает"));
        else 
        	this.buttonList.add(offMod = new GuiButton(0, this.width / 2 - 100, this.height - (this.height / 4) - 50, "Выключено"));
        
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
        	if (!aaaa) {
        		aaaa = true;
        		offMod.displayString = "Работает";
        	} else {
        		aaaa = false;
        		offMod.displayString = "Выключено";
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
