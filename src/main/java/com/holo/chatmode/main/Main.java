package com.holo.chatmode.main;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;

import com.holo.chatmode.proxy.CommonProxy;
import com.holo.chatmode.reference.Reference;


@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION,
acceptedMinecraftVersions = Reference.ACCEPTED_MINECRAFT_VERSION)
public class Main {

	@Instance
	public static Main instance;
	
	@SidedProxy(clientSide = Reference.CLIENT, serverSide = Reference.COMMON)
	public static CommonProxy proxy;
}
