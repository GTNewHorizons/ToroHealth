package net.torocraft.torohealthmod.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.torocraft.torohealthmod.client.configuration.ConfigurationHandler;

import org.lwjgl.opengl.GL11;

public class DamageParticles extends EntityFX {

    private static final float GRAVITY = 0.1F;
    private static final int LIFESPAN = 12;

    private static final Minecraft mc = Minecraft.getMinecraft();

    private final String text;
    private final int color;
    private float prevParticleScale;
    private boolean grow = true;

    private DamageParticles(int damage, World world, double parX, double parY, double parZ, double parMotionX,
            double parMotionY, double parMotionZ) {
        super(world, parX, parY, parZ, parMotionX, parMotionY, parMotionZ);
        this.particleTextureJitterX = 0.0F;
        this.particleTextureJitterY = 0.0F;
        this.particleGravity = GRAVITY;
        this.particleScale = (float) ConfigurationHandler.size;
        this.prevParticleScale = this.particleScale;
        this.particleMaxAge = LIFESPAN;
        this.text = Integer.toString(Math.abs(damage));
        this.color = damage < 0 ? ConfigurationHandler.healColor : ConfigurationHandler.damageColor;
    }

    public static void spawnDamageParticle(EntityLivingBase entity, int damage) {
        if (isEntityNotVisible(entity)) return;
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

    private static boolean isEntityNotVisible(EntityLivingBase entity) {
        final EntityClientPlayerMP player = mc.thePlayer;
        if (entity.isInvisibleToPlayer(player)) {
            return true;
        }
        final double distSq = player.getDistanceSqToEntity(entity);
        if (distSq > 64D * 64D) {
            // the entity is too far
            return true;
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
                return true;
            }
        }
        return !ConfigurationHandler.showThroughWalls
                && player.worldObj.func_147447_a(playerEyePos, entityEyePos, false, true, true) != null;
        // rayTraceBlocks(Vec3 from, Vec3 to, boolean stopOnLiquid, boolean ignoreBlockWithoutBoundingBox, boolean
        // returnLastUncollidableBlock)
    }

    @Override
    public void renderParticle(Tessellator tessellator, float partialTicks, float rotationX, float rotationZ,
            float rotationYZ, float rotationXY, float rotationXZ) {
        final float relativeX;
        final float relativeY;
        final float relativeZ;
        final float rotationYaw;
        final float rotationPitch;
        relativeX = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) partialTicks - interpPosX);
        relativeY = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) partialTicks - interpPosY);
        relativeZ = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) partialTicks - interpPosZ);
        if (mc.gameSettings.thirdPersonView != 2) {
            rotationYaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
            rotationPitch = Minecraft.getMinecraft().thePlayer.rotationPitch;
        } else {
            rotationYaw = Minecraft.getMinecraft().thePlayer.rotationYaw + 180F;
            rotationPitch = -Minecraft.getMinecraft().thePlayer.rotationPitch;
        }
        GL11.glPushMatrix();
        GL11.glTranslatef(relativeX, relativeY, relativeZ);
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-rotationYaw, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(rotationPitch, 1.0F, 0.0F, 0.0F);
        final double f1 = (this.prevParticleScale + (this.particleScale - this.prevParticleScale) * partialTicks)
                * 0.008D;
        GL11.glScaled(-f1, -f1, f1);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        final int j = mc.fontRenderer.getStringWidth(this.text) / 2;
        GL11.glDepthMask(false);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        mc.fontRenderer.drawStringWithShadow(this.text, -j, 0, this.color);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glPopMatrix();
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.prevParticleScale = this.particleScale;
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
