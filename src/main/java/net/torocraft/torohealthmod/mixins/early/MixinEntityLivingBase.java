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
    private int torohealth$prevMaxHealth = -1;

    @Override
    public int torohealth$getPrevHealth() {
        return torohealth$prevHealth;
    }

    @Override
    public int torohealth$getPrevMaxHealth() {
        return torohealth$prevMaxHealth;
    }

    @Override
    public void torohealth$setPrevHealth(int prevHealth) {
        this.torohealth$prevHealth = prevHealth;
    }

    @Override
    public void torohealth$setPrevMaxHealth(int prevMaxHealth) {
        this.torohealth$prevMaxHealth = prevMaxHealth;
    }

}
