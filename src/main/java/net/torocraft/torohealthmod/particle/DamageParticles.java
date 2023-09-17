package net.torocraft.torohealthmod.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.torocraft.torohealthmod.configuration.ConfigurationHandler;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class DamageParticles extends EntityFX {

    private static final float GRAVITY = 0.1F;
    private static final int LIFESPAN = 12;

    private static final Minecraft mc = Minecraft.getMinecraft();

    private final String text;
    private boolean grow = true;
    private final int damage;

    private DamageParticles(int damage, World world, double parX, double parY, double parZ, double parMotionX,
            double parMotionY, double parMotionZ) {
        super(world, parX, parY, parZ, parMotionX, parMotionY, parMotionZ);
        this.particleTextureJitterX = 0.0F;
        this.particleTextureJitterY = 0.0F;
        this.particleGravity = GRAVITY;
        this.particleScale = (float) ConfigurationHandler.size;
        this.particleMaxAge = LIFESPAN;
        this.damage = damage;
        this.text = Integer.toString(Math.abs(damage));
    }

    public static void spawnDamageParticle(EntityLivingBase entity, int damage) {
        if (!ConfigurationHandler.showAlways && !canEntityBeSeen(entity)) return;
        final double motionX = entity.worldObj.rand.nextGaussian() * 0.02;
        final double motionY = 0.5f;
        final double motionZ = entity.worldObj.rand.nextGaussian() * 0.02;
        final DamageParticles damageIndicator = new DamageParticles(
                damage,
                entity.worldObj,
                entity.posX,
                entity.posY + entity.height,
                entity.posZ,
                motionX,
                motionY,
                motionZ);
        mc.effectRenderer.addEffect(damageIndicator);
    }

    private static boolean canEntityBeSeen(EntityLivingBase entity) {
        final EntityClientPlayerMP player = mc.thePlayer;
        final double distSq = player.getDistanceSqToEntity(entity);
        if (distSq > 64D * 64D) {
            // the entity is too far
            return false;
        }
        final Vec3 playerEyePos = Vec3
                .createVectorHelper(player.posX, player.posY + (double) player.getEyeHeight(), player.posZ);
        final Vec3 entityEyePos = Vec3
                .createVectorHelper(entity.posX, entity.posY + (double) entity.getEyeHeight(), entity.posZ);
        if (distSq > 25D) {
            final Vec3 playerLook = player.getLookVec();
            final Vec3 playerToEntity = playerEyePos.subtract(entityEyePos);
            if ((mc.gameSettings.thirdPersonView == 2 ? -1D : 1D) * playerLook.dotProduct(playerToEntity) < 0D) {
                // the entity is behind
                return false;
            }
        }
        // rayTraceBlocks(Vec3 vec31, Vec3 vec32, boolean stopOnLiquid, boolean ignoreBlockWithoutBoundingBox, boolean
        // returnLastUncollidableBlock)
        return player.worldObj.func_147447_a(playerEyePos, entityEyePos, false, true, false) == null;
    }

    @Override
    public void renderParticle(Tessellator p_70539_1_, float partialTicks, float rotationX, float rotationZ,
            float rotationYZ, float rotationXY, float rotationXZ) {
        float rotationYaw = (-mc.thePlayer.rotationYaw);
        float rotationPitch = mc.thePlayer.rotationPitch;
        float f11 = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) partialTicks - interpPosX);
        float f12 = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) partialTicks - interpPosY);
        float f13 = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) partialTicks - interpPosZ);

        GL11.glPushMatrix();
        GL11.glDepthFunc(519);
        GL11.glTranslatef(f11, f12, f13);
        GL11.glRotatef(rotationYaw, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(rotationPitch, 1.0F, 0.0F, 0.0F);

        GL11.glScalef(-1.0F, -1.0F, 1.0F);
        GL11.glScaled(this.particleScale * 0.008D, this.particleScale * 0.008D, this.particleScale * 0.008D);
        float scale = 1.0F;
        GL11.glScaled(scale, scale, scale);

        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 0.003662109F);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDepthMask(true);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDisable(2896);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glEnable(3008);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        int color = ConfigurationHandler.damageColor;
        if (damage < 0) {
            color = ConfigurationHandler.healColor;
        }

        final FontRenderer fontRenderer = mc.fontRenderer;
        fontRenderer.drawStringWithShadow(
                this.text,
                -MathHelper.floor_float(fontRenderer.getStringWidth(this.text) / 2.0F) + 1,
                -MathHelper.floor_float(fontRenderer.FONT_HEIGHT / 2.0F) + 1,
                color);

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDepthFunc(515);
        GL11.glPopMatrix();
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.grow) {
            this.particleScale *= 1.1664F;
            if (this.particleScale > ConfigurationHandler.size * 3.0D) {
                this.grow = false;
            }
        } else {
            this.particleScale *= 0.8573F;
        }
    }

    public int getFXLayer() {
        return 3;
    }

}
