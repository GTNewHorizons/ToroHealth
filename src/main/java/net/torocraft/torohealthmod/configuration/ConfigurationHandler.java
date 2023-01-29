package net.torocraft.torohealthmod.configuration;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.torocraft.torohealthmod.ToroHealthMod;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ConfigurationHandler {

    public static Configuration config;
    public static boolean showDamageParticles = true;
    public static boolean showAlways = false;
    public static Integer damageColor;
    public static Integer healColor;
    public static double size = 3.0;
    private static final String[] acceptedColors = new String[] { "RED", "GREEN", "BLUE", "YELLOW", "ORANGE", "WHITE",
            "BLACK", "PURPLE" };

    public static void init(String configDir) {
        if (config == null) {
            File path = new File(configDir + "/" + ToroHealthMod.MODID + ".cfg");
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
        if (color.equals("RED")) {
            return 0xff0000;
        } else if (color.equals("GREEN")) {
            return 0x00ff00;
        } else if (color.equals("BLUE")) {
            return 0x0000ff;
        } else if (color.equals("YELLOW")) {
            return 0xffff00;
        } else if (color.equals("ORANGE")) {
            return 0xffa500;
        } else if (color.equals("BLACK")) {
            return 0x000000;
        } else if (color.equals("PURPLE")) {
            return 0x960096;
        } else {
            return 0xffffff;
        }
    }
}
