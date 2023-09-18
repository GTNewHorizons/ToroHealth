package net.torocraft.torohealthmod.client.configuration.gui;

import static net.torocraft.torohealthmod.client.configuration.ConfigurationHandler.getConfig;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.torocraft.torohealthmod.ToroHealthMod;

import cpw.mods.fml.client.config.GuiConfig;

public class ModGUIConfig extends GuiConfig {

    public ModGUIConfig(GuiScreen guiScreen) {
        super(
                guiScreen,
                new ConfigElement<>(getConfig().getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
                ToroHealthMod.MODID,
                false,
                false,
                GuiConfig.getAbridgedConfigPath(getConfig().toString()));
    }

}
