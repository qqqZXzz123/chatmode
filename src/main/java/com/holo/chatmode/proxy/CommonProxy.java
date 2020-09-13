package com.holo.chatmode.proxy;

import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

import com.holo.chatmode.util.Handler;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy
{
    public void preInit(final FMLPreInitializationEvent event) {
    	MinecraftForge.EVENT_BUS.register(new Handler());
    }
    
    public void init(final FMLInitializationEvent event) {
    }
    
    public void postInit(final FMLPostInitializationEvent event) {
    }
}
