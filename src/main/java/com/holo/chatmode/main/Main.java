package com.holo.chatmode.main;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import com.holo.chatmode.proxy.CommonProxy;
import com.holo.chatmode.reference.Reference;


@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION,
acceptedMinecraftVersions = Reference.ACCEPTED_MINECRAFT_VERSION)
public class Main {

	@Instance
	public static Main instance;
	
	@SidedProxy(clientSide = Reference.CLIENT, serverSide = Reference.COMMON)
	public static CommonProxy proxy;
	
	@EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        Main.proxy.preInit(event);
    }
    
    @EventHandler
    public void init(final FMLInitializationEvent event) {
        Main.proxy.init(event);
    }
    
    @EventHandler
    public void postInit(final FMLPostInitializationEvent event) {
        Main.proxy.postInit(event);
    }
}
