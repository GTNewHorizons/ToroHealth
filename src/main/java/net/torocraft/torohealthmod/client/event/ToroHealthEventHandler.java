package net.torocraft.torohealthmod.client.event;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.torocraft.torohealthmod.client.particle.DamageParticles;
import net.torocraft.torohealthmod.mixins.interfaces.EntityLivingBaseExt;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ToroHealthEventHandler {

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        final EntityLivingBase entity = event.entityLiving;
        if (!entity.worldObj.isRemote) return;

        final int prevHealth = ((EntityLivingBaseExt) entity).torohealth$getPrevHealth();
        final int health = MathHelper.floor_float(entity.getHealth());

        if (prevHealth != health) {
            ((EntityLivingBaseExt) entity).torohealth$setPrevHealth(health);

            // -1 means that prevHealth wasn't initialized because the Living has just spawned
            if (prevHealth != -1) {
                DamageParticles.spawnDamageParticle(entity, prevHealth - health);
            }
        }
    }

}
