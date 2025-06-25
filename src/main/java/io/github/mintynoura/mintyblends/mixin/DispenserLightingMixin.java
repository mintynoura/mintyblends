package io.github.mintynoura.mintyblends.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.mintynoura.mintyblends.block.KettleBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net.minecraft.block.dispenser.DispenserBehavior$12")
public abstract class DispenserLightingMixin extends FallibleItemDispenserBehavior {

    @ModifyExpressionValue(method = "dispenseSilently", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/CampfireBlock;canBeLit(Lnet/minecraft/block/BlockState;)Z"))
    private boolean mintyblends$addKettleLighting(boolean original, @Local BlockState blockState, @Local ServerWorld serverWorld, @Local BlockPos blockPos) {
        return original || KettleBlock.canBeLit(blockState, serverWorld, blockPos);
    }
}
