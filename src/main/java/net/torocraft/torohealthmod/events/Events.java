package net.torocraft.torohealthmod.events;

import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.torocraft.torohealthmod.ToroHealthMod;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class Events {

    @SubscribeEvent
    public void displayDamage(LivingUpdateEvent event) {
        ToroHealthMod.proxy.displayDamageDealt(event.entityLiving);
    }

}
