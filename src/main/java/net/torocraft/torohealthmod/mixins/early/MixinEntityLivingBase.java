package net.torocraft.torohealthmod.mixins.early;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.torocraft.torohealthmod.mixins.interfaces.EntityLivingBaseExt;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(EntityLivingBase.class)
public abstract class MixinEntityLivingBase implements EntityLivingBaseExt {

    @Unique
    private int torohealth$prevHealth;

    @Unique
    public int getTorohealth$prevHealth() {
        return torohealth$prevHealth;
    }

    @Unique
    public void setTorohealth$prevHealth(int hp) {
        this.torohealth$prevHealth = hp;
    }

    @ModifyArg(
            method = "<init>",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;setHealth(F)V"))
    private float torohealth$initHelth(float hp) {
        this.torohealth$prevHealth = MathHelper.floor_float(hp);
        return hp;
    }

}
