//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package io.github.mintynoura.mintyblends.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;

@Environment(EnvType.CLIENT)
public class KettleSteamParticle extends AscendingParticle {

    protected KettleSteamParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, float scaleMultiplier, SpriteProvider spriteProvider) {
        super(world, x, y, z, 0.1F, 0.1F, 0.1F, velocityX, velocityY, velocityZ, scaleMultiplier, spriteProvider, 0.2f, 8, -0.1F, true);
        this.maxAge = 25;
        this.setColor(1f, 1f, 1f);
    }

    @Override
    public void tick() {
        super.tick();
        if (!(this.alpha <= 0f) && this.alpha > 0.1f) {
            this.alpha -= 0.015f;
        }
    }

    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }


    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(SimpleParticleType simpleParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            KettleSteamParticle kettleSteamParticle = new KettleSteamParticle(clientWorld, d, e, f, g, h, i, 2.0f, this.spriteProvider);
            kettleSteamParticle.setAlpha(0.9f);
            return kettleSteamParticle;
        }
    }
}
