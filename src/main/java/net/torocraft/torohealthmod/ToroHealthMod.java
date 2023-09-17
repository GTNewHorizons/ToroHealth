package net.torocraft.torohealthmod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagInt;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.torocraft.torohealthmod.configuration.ConfigurationHandler;
import net.torocraft.torohealthmod.particle.DamageParticles;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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
        }
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        if (event.getSide() == Side.CLIENT) {
            MinecraftForge.EVENT_BUS.register(this);
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        if (!ConfigurationHandler.showDamageParticles) return;
        final EntityLivingBase entity = event.entityLiving;
        if (!entity.worldObj.isRemote) return;
        final int currentHealth = (int) Math.ceil(entity.getHealth());
        if (entity.getEntityData().hasKey("health")) {
            final int entityHealth = ((NBTTagInt) entity.getEntityData().getTag("health")).func_150287_d();
            if (entityHealth != currentHealth) {
                displayParticle(entity, entityHealth - currentHealth);
            }
        }
        entity.getEntityData().setTag("health", new NBTTagInt(currentHealth));
    }

    @SideOnly(Side.CLIENT)
    private void displayParticle(EntityLivingBase entity, int damage) {
        if (damage == 0) return;
        if (!ConfigurationHandler.showAlways && !entity.canEntityBeSeen(Minecraft.getMinecraft().thePlayer)) return;
        final double motionX = entity.worldObj.rand.nextGaussian() * 0.02;
        final double motionY = 0.5f;
        final double motionZ = entity.worldObj.rand.nextGaussian() * 0.02;
        final EntityFX damageIndicator = new DamageParticles(
                damage,
                entity.worldObj,
                entity.posX,
                entity.posY + entity.height,
                entity.posZ,
                motionX,
                motionY,
                motionZ);
        Minecraft.getMinecraft().effectRenderer.addEffect(damageIndicator);
    }

}
