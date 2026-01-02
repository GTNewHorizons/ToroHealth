package net.torocraft.torohealthmod.mixins.early;

import net.minecraft.entity.EntityLivingBase;
import net.torocraft.torohealthmod.mixins.interfaces.EntityLivingBaseExt;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(EntityLivingBase.class)
public abstract class MixinEntityLivingBase implements EntityLivingBaseExt {

    @Unique
    private int torohealth$prevHealth = -1;

    @Unique
    public int torohealth$getPrevHealth() {
        return torohealth$prevHealth;
    }

    @Unique
    public void torohealth$setPrevHealth(int prevHealth) {
        this.torohealth$prevHealth = prevHealth;
    }

}
