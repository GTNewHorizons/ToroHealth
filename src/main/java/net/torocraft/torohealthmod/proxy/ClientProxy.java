package net.torocraft.torohealthmod.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.world.World;
import net.torocraft.torohealthmod.configuration.ConfigurationHandler;
import net.torocraft.torohealthmod.render.DamageParticles;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);
    }


    @Override
    public void displayDamageDealt(EntityLivingBase entity) {

        if (!entity.worldObj.isRemote)
            return;
        if (!ConfigurationHandler.showDamageParticles)
            return;

        int currentHealth = (int) Math.ceil(entity.getHealth());

        if (entity.getEntityData().hasKey("health")) {
            int entityHealth = ((NBTTagInt) entity.getEntityData().getTag("health")).func_150287_d();

            if (entityHealth != currentHealth) {
                displayParticle(entity, entityHealth - currentHealth);
            }
        }

        entity.getEntityData().setTag("health", new NBTTagInt(currentHealth));
    }

    private void displayParticle(EntityLivingBase entity, int damage) {

        if (damage == 0)
            return;
        if (!entity.canEntityBeSeen(Minecraft.getMinecraft().thePlayer) && !ConfigurationHandler.showAlways)
            return;

        World world = entity.worldObj;
        double motionX = world.rand.nextGaussian() * 0.02;
        double motionY = 0.5f;
        double motionZ = world.rand.nextGaussian() * 0.02;
        EntityFX damageIndicator = new DamageParticles(damage, world, entity.posX, entity.posY + entity.height, entity.posZ, motionX, motionY, motionZ);
        Minecraft.getMinecraft().effectRenderer.addEffect(damageIndicator);
    }

}