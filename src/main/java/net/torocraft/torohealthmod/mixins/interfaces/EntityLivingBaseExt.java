package net.torocraft.torohealthmod.mixins.interfaces;

public interface EntityLivingBaseExt {

    int torohealth$getPrevHealth();

    int torohealth$getPrevMaxHealth();

    void torohealth$setPrevHealth(int torohealth$prevHealth);

    void torohealth$setPrevMaxHealth(int torohealth$prevMaxHealth);
}
