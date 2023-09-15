package net.torocraft.torohealthmod.configuration;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.torocraft.torohealthmod.ToroHealthMod;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ConfigurationHandler {

    private static Configuration config;
    public static boolean showDamageParticles = true;
    public static boolean showAlways = false;
    public static int damageColor;
    public static int healColor;
    public static double size = 3.0;
    private static final String[] acceptedColors = new String[] { "RED", "GREEN", "BLUE", "YELLOW", "ORANGE", "WHITE",
            "BLACK", "PURPLE" };

    public ConfigurationHandler(File path) {
        if (config == null) {
            config = new Configuration(path);
            loadConfiguration();
        }
    }

    private static void loadConfiguration() {
        showDamageParticles = config
                .getBoolean("Show Damage Particles", Configuration.CATEGORY_GENERAL, true, "Show Damage Indicators");
        showAlways = config.getBoolean(
                "Show Always Particles",
                Configuration.CATEGORY_GENERAL,
                false,
                "Show Always The Damage Particles");
        size = config.get(Configuration.CATEGORY_GENERAL, "Particles Size", size, "Particles Size [default: 3.0]")
                .getDouble();
        healColor = mapColor(
                config.getString(
                        "Heal Color",
                        Configuration.CATEGORY_GENERAL,
                        "GREEN",
                        "Heal Text Color",
                        acceptedColors));
        damageColor = mapColor(
                config.getString(
                        "Damage Color",
                        Configuration.CATEGORY_GENERAL,
                        "RED",
                        "Damage Text Color",
                        acceptedColors));

        if (config.hasChanged()) config.save();
    }

    @SubscribeEvent
    public void onConfigurationChangeEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equalsIgnoreCase(ToroHealthMod.MODID)) loadConfiguration();
    }

    public static Configuration getConfig() {
        return config;
    }

    private static int mapColor(String color) {
        switch (color) {
            case "RED":
                return 0xFF0000;
            case "GREEN":
                return 0x00FF00;
            case "BLUE":
                return 0x0000FF;
            case "YELLOW":
                return 0xFFFF00;
            case "ORANGE":
                return 0xFFA500;
            case "BLACK":
                return 0x000000;
            case "PURPLE":
                return 0x960096;
            default:
                return 0xFFFFFF;
        }
    }
}
