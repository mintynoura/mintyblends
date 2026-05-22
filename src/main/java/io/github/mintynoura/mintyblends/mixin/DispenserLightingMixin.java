package io.github.mintynoura.mintyblends.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.mintynoura.mintyblends.block.KettleBlock;
import io.github.mintynoura.mintyblends.registry.MintyBlendsBlocks;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net.minecraft.core.dispenser.DispenseItemBehavior$5")
public abstract class DispenserLightingMixin extends OptionalDispenseItemBehavior {

    @ModifyExpressionValue(method = "execute", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/CampfireBlock;canLight(Lnet/minecraft/world/level/block/state/BlockState;)Z"))
    private boolean mintyblends$addKettleLighting(boolean original, @Local(name = "target") BlockState target) {
        return target.is(MintyBlendsBlocks.KETTLE) ? KettleBlock.canBeLit(target) : original;
    }
}
