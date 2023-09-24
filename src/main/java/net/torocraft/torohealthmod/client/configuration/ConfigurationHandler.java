package net.torocraft.torohealthmod.client.configuration;

import java.io.File;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.torocraft.torohealthmod.ToroHealthMod;
import net.torocraft.torohealthmod.client.event.ToroHealthEventHandler;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ConfigurationHandler {

    private static Configuration config;
    public static boolean showDamageParticles = true;
    public static boolean showThroughWalls = false;
    public static int damageColor;
    public static int healColor;
    public static double size = 3.0;
    private static final String[] acceptedColors = new String[] { "RED", "GREEN", "BLUE", "YELLOW", "ORANGE", "WHITE",
            "BLACK", "PURPLE" };
    private static ToroHealthEventHandler eventHandler;

    public ConfigurationHandler(File path) {
        if (config == null) {
            config = new Configuration(path);
            loadConfiguration();
        }
    }

    private static void loadConfiguration() {
        // spotless:off
        showDamageParticles = config.getBoolean("Show Damage Particles", Configuration.CATEGORY_GENERAL, true, "Show Damage Indicators");
        showThroughWalls =    config.getBoolean("Render through walls", Configuration.CATEGORY_GENERAL, false, "Render particles though walls");
        size =                config.get(Configuration.CATEGORY_GENERAL, "Particles Size", size, "Particles Size [default: 3.0]").getDouble();
        healColor =   mapColor(config.getString("Heal Color", Configuration.CATEGORY_GENERAL, "GREEN", "Heal Text Color", acceptedColors));
        damageColor = mapColor(config.getString("Damage Color", Configuration.CATEGORY_GENERAL, "RED", "Damage Text Color", acceptedColors));
        // spotless:on

        if (showDamageParticles) {
            if (eventHandler == null) {
                eventHandler = new ToroHealthEventHandler();
                MinecraftForge.EVENT_BUS.register(eventHandler);
            }
        } else {
            if (eventHandler != null) {
                MinecraftForge.EVENT_BUS.unregister(eventHandler);
                eventHandler = null;
            }
        }

        if (config.hasChanged()) {
            config.save();
        }

    }

    @SubscribeEvent
    public void onConfigurationChangeEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equalsIgnoreCase(ToroHealthMod.MODID)) {
            loadConfiguration();
        }
    }

    public static Configuration getConfig() {
        return config;
    }

    private static int mapColor(String color) {
        return switch (color) {
            case "RED" -> 0xFF0000;
            case "GREEN" -> 0x00FF00;
            case "BLUE" -> 0x0000FF;
            case "YELLOW" -> 0xFFFF00;
            case "ORANGE" -> 0xFFA500;
            case "BLACK" -> 0x000000;
            case "PURPLE" -> 0x960096;
            default -> 0xFFFFFF;
        };
    }

}
