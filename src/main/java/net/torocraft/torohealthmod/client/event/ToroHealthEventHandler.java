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
        final int prevHp = MathHelper.floor_float(((EntityLivingBaseExt) entity).getTorohealth$prevHealth());
        final int hp = MathHelper.floor_float(entity.getHealth());
        if (hp > prevHp + 2 && entity.ticksExisted < 5) {
            // the mob just spawned, and it mistakenly
            // renders their whole health as healing
            return;
        }
        if (prevHp != hp) {
            DamageParticles.spawnDamageParticle(entity, prevHp - hp);
            ((EntityLivingBaseExt) entity).setTorohealth$prevHealth(hp);
        }
    }

}
