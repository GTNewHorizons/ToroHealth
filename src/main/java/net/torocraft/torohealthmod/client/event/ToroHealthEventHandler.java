package net.torocraft.torohealthmod.client.event;

import net.minecraft.util.MathHelper;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.torocraft.torohealthmod.client.particle.DamageParticles;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ToroHealthEventHandler {

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onLivingHurt(LivingHurtEvent event) {
        DamageParticles.spawnDamageParticle(event.entityLiving, MathHelper.floor_float(event.ammount));
    }

}
