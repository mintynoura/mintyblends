package io.github.mintynoura.mintyblends.mixin;

import net.minecraft.entity.passive.OcelotEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(OcelotEntity.class)
public interface OcelotEntityInvoker {
    @Invoker
    boolean invokeIsTrusting();
    @Invoker
    void invokeSetTrusting(boolean trusting);
    @Invoker
    void invokeShowEmoteParticle(boolean positive);
}
