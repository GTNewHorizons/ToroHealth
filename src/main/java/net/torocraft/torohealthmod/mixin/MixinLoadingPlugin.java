package net.torocraft.torohealthmod.mixin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.gtnewhorizon.gtnhmixins.IEarlyMixinLoader;

import cpw.mods.fml.relauncher.FMLLaunchHandler;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

public class MixinLoadingPlugin implements IEarlyMixinLoader, IFMLLoadingPlugin {

    @Override
    public String[] getASMTransformerClass() {
        return null;
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {}

    @Override
    public String getAccessTransformerClass() {
        return null;
    }

    @Override
    public String getMixinConfig() {
        return "mixins.torohealthmod.early.json";
    }

    @Override
    public List<String> getMixins(Set<String> loadedCoreMods) {
        final ArrayList<String> list = new ArrayList<>();
        if (FMLLaunchHandler.side().isClient()) {
            list.add("MixinEntityLivingBase");
        }
        return list;
    }

}
