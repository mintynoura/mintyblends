package io.github.mintynoura.mintyblends.particle;


import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import org.jspecify.annotations.NullMarked;

@Environment(EnvType.CLIENT)
@NullMarked
public class KettleSteamParticle extends SingleQuadParticle {

    protected KettleSteamParticle(ClientLevel level, double x, double y, double z, double velocityX, double velocityY, double velocityZ, float scaleMultiplier, TextureAtlasSprite textureAtlasSprite) {
        super(level, x, y, z, velocityX, velocityY, velocityZ, textureAtlasSprite);
        this.lifetime = 25;
        this.setColor(1f, 1f, 1f);
    }

    @Override
    public void tick() {
        super.tick();
        if (!(this.alpha <= 0f) && this.alpha > 0.1f) {
            this.alpha -= 0.015f;
        }
    }


    @Override
    protected Layer getLayer() {
        return Layer.TRANSLUCENT;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;

        public Factory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i, RandomSource random) {
            KettleSteamParticle kettleSteamParticle = new KettleSteamParticle(clientWorld, d, e, f, g, h, i, 2.0f, this.spriteProvider.get(random));
            kettleSteamParticle.setAlpha(0.9f);
            return kettleSteamParticle;
        }
    }
}
