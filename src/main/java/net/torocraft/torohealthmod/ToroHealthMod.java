package net.torocraft.torohealthmod;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;
import net.torocraft.torohealthmod.events.Events;
import net.torocraft.torohealthmod.proxy.CommonProxy;

@Mod(modid = ToroHealthMod.MODID, name = ToroHealthMod.MODNAME, version = ToroHealthMod.VERSION, guiFactory = ToroHealthMod.GUI_FACTORY_CLASS)
public class ToroHealthMod {

	public static final String MODID = "torohealthmod";
	public static final String VERSION = "GRADLETOKEN_VERSION";
	public static final String MODNAME = "ToroHealthMod";
	public static final String GUI_FACTORY_CLASS = "net.torocraft.torohealthmod.configuration.gui.GuiFactory";

	@SidedProxy(clientSide = "net.torocraft.torohealthmod.proxy.ClientProxy", serverSide = "net.torocraft.torohealthmod.proxy.CommonProxy")
	public static CommonProxy proxy;

	@Mod.Instance(value = ToroHealthMod.MODID)
	public static ToroHealthMod instance;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		proxy.preInit(e);
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent e) {
		proxy.init(e);
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		MinecraftForge.EVENT_BUS.register(new Events());
	}

}
