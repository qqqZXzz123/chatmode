package com.holo.chatmode.proxy;

import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;

public class ClientProxy extends CommonProxy
{
    public static KeyBinding keyBindings;
    
    @Override
    public void preInit(final FMLPreInitializationEvent event) {
        super.preInit(event);
    }
    
    @Override
    public void init(final FMLInitializationEvent event) {
        ClientRegistry.registerKeyBinding(ClientProxy.keyBindings = new KeyBinding("NexusChest with love", Keyboard.KEY_G, "key.categories.gameplay"));
        super.init(event);
    }
    
    @Override
    public void postInit(final FMLPostInitializationEvent event) {
        super.postInit(event);
    }
}
