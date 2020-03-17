package net.torocraft.torohealthmod;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.common.MinecraftForge;
import net.torocraft.torohealthmod.events.Events;
import net.torocraft.torohealthmod.proxy.CommonProxy;

@Mod(modid = ToroHealthMod.MODID, name = ToroHealthMod.MODNAME, version = ToroHealthMod.VERSION)
public class ToroHealthMod {

	public static final String MODID = "torohealthmod";
	public static final String VERSION = "1.7.10-1.0.0";
	public static final String MODNAME = "ToroHealthMod";

	@SidedProxy(clientSide = "net.torocraft.torohealthmod.proxy.ClientProxy", serverSide = "net.torocraft.torohealthmod.ServerProxy")
	public static CommonProxy proxy;

	@Mod.Instance(value = ToroHealthMod.MODID)
	public static ToroHealthMod instance;

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		MinecraftForge.EVENT_BUS.register(new Events());
	}

}
