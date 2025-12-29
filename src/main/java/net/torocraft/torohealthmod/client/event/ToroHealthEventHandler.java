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

        final int prevHp = ((EntityLivingBaseExt) entity).torohealth$getPrevHealth();
        final int hp = MathHelper.floor_float(entity.getHealth());

        if (prevHp != hp) {
            ((EntityLivingBaseExt) entity).torohealth$setPrevHealth(hp);

            // -1 means that prevHealth was uninitialized because the Living has just spawned
            if (prevHp != -1) {
                DamageParticles.spawnDamageParticle(entity, prevHp - hp);
            }
        }
    }

}
