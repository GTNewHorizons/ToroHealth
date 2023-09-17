package net.torocraft.torohealthmod;

import java.util.logging.Logger;

import net.torocraft.torohealthmod.configuration.ConfigurationHandler;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;

@Mod(
        modid = ToroHealthMod.MODID,
        name = ToroHealthMod.MODNAME,
        version = ToroHealthMod.VERSION,
        guiFactory = ToroHealthMod.GUI_FACTORY_CLASS)
public class ToroHealthMod {

    public static final String MODID = "GRADLETOKEN_MODID";
    public static final String VERSION = "GRADLETOKEN_VERSION";
    public static final String MODNAME = "ToroHealthMod";
    public static final String GUI_FACTORY_CLASS = "net.torocraft.torohealthmod.configuration.gui.GuiFactory";

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        if (event.getSide() == Side.CLIENT) {
            FMLCommonHandler.instance().bus().register(new ConfigurationHandler(event.getSuggestedConfigurationFile()));
        } else if (event.getSide() == Side.SERVER) {
            Logger.getLogger(MODNAME).info(
                    MODNAME + " has been installed on a server, this is a client mod, it serves no purpose on a server and can be safely removed");
        }
    }

}
