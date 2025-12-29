package net.torocraft.torohealthmod.mixins.early;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.torocraft.torohealthmod.mixins.interfaces.EntityLivingBaseExt;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityLivingBase.class)
public abstract class MixinEntityLivingBase implements EntityLivingBaseExt {

    @Unique
    private int torohealth$prevHealth = -1;

    @Unique
    public int torohealth$getPrevHealth() {
        return torohealth$prevHealth;
    }

    @Unique
    public void torohealth$setPrevHealth(int hp) {
        this.torohealth$prevHealth = hp;
    }

    @Inject(method = "readEntityFromNBT(Lnet/minecraft/nbt/NBTTagCompound;)V", at = @At("RETURN"))
    private void torohealth$initHealthFromNBT(NBTTagCompound tag, CallbackInfo ci) {
        EntityLivingBase self = (EntityLivingBase) (Object) this;
        this.torohealth$prevHealth = MathHelper.floor_float(self.getHealth());
    }

}
