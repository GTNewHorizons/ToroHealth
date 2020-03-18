package net.torocraft.torohealthmod.configuration;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;
import net.torocraft.torohealthmod.ToroHealthMod;

import java.io.File;

public class ConfigurationHandler {

    public static Configuration config;
    public static boolean showDamageParticles = true;
    public static Integer damageColor;
    public static Integer healColor;
    public static float size = 3.0F;
    private static String[] acceptedColors = new String[] { "RED", "GREEN", "BLUE", "YELLOW", "ORANGE", "WHITE", "BLACK", "PURPLE" };

    public static void init(String configDir) {
        if(config == null){
            File path = new File(configDir + "/" + ToroHealthMod.MODID + ".cfg");
            config = new Configuration(path);
            loadConfiguration();
        }
    }

    private static void loadConfiguration() {
        showDamageParticles = config.getBoolean("Show Damage Particles", Configuration.CATEGORY_GENERAL, true, "Show Damage Indicators");
        size= config.getFloat("Particles Size", Configuration.CATEGORY_GENERAL, 3.0F, 0.0F, 10.0F, "Particles Size");
        healColor = mapColor(config.getString("Heal Color", Configuration.CATEGORY_GENERAL, "GREEN", "Heal Text Color", acceptedColors));
        damageColor = mapColor(config.getString("Damage Color", Configuration.CATEGORY_GENERAL, "RED", "Damage Text Color", acceptedColors));

        if(config.hasChanged())
            config.save();
    }

    @SubscribeEvent
    public void onConfigurationChangeEvent (ConfigChangedEvent.OnConfigChangedEvent event){
        if(event.modID.equalsIgnoreCase(ToroHealthMod.MODID))
            loadConfiguration();
    }

    public static Configuration getConfig(){
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
