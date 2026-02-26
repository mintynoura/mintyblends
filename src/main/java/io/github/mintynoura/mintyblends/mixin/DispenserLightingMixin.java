package io.github.mintynoura.mintyblends.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.mintynoura.mintyblends.block.KettleBlock;
import io.github.mintynoura.mintyblends.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net.minecraft.core.dispenser.DispenseItemBehavior$6")
public abstract class DispenserLightingMixin extends OptionalDispenseItemBehavior {

    @ModifyExpressionValue(method = "dispenseSilently", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/CampfireBlock;canLight(Lnet/minecraft/world/level/block/state/BlockState;)Z"))
    private boolean mintyblends$addKettleLighting(boolean original, @Local BlockState blockState, @Local ServerLevel serverWorld, @Local BlockPos blockPos) {
        return blockState.is(ModBlocks.KETTLE) ? KettleBlock.canBeLit(blockState, serverWorld, blockPos) : original;
    }
}
