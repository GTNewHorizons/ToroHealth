package net.torocraft.torohealthmod.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.world.World;
import net.torocraft.torohealthmod.render.DamageParticles;

public class ClientProxy extends CommonProxy {

    @Override
    public void displayDamageDealt(EntityLivingBase entity) {

        if (!entity.worldObj.isRemote) {
            return;
        }

        int currentHealth = (int) Math.ceil(entity.getHealth());

        if (entity.getEntityData().hasKey("health")) {
            int entityHealth = ((NBTTagInt) entity.getEntityData().getTag("health")).func_150287_d();

            if (entityHealth != currentHealth) {
                displayParticle(entity, (int) entityHealth - currentHealth);
            }
        }

        entity.getEntityData().setTag("health", new NBTTagInt(currentHealth));
    }

    private void displayParticle(Entity entity, int damage) {
        if (damage == 0) {
            return;
        }

        World world = entity.worldObj;
        double motionX = world.rand.nextGaussian() * 0.02;
        double motionY = 0.5f;
        double motionZ = world.rand.nextGaussian() * 0.02;
        DamageParticles damageIndicator = new DamageParticles(damage, world, entity.posX, entity.posY + entity.height, entity.posZ, motionX, motionY, motionZ);
        Minecraft.getMinecraft().effectRenderer.addEffect(damageIndicator);
    }

}