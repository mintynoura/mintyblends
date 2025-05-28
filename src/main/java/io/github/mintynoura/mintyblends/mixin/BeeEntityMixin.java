package io.github.mintynoura.mintyblends.mixin;

import io.github.mintynoura.mintyblends.block.HerbBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldEvents;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Debug(export = true)
@Mixin(targets = "net.minecraft.entity.passive.BeeEntity$GrowCropsGoal")
public abstract class BeeEntityMixin extends Goal {

    @Final
    @Shadow
    BeeEntity field_20373;

    @Inject(method = "tick()V", at = @At("TAIL"))
    private void tickHerb(CallbackInfo info) {
        if (field_20373.getRandom().nextInt(this.getTickCount(30)) == 0) {
            for (int i = 1; i <= 2; i++) {
                BlockPos herbPos = field_20373.getBlockPos().down(i);
                BlockState herbBlock = field_20373.getWorld().getBlockState(herbPos);
                Block herb = herbBlock.getBlock();
                if (herbBlock.isIn(BlockTags.BEE_GROWABLES) && herb instanceof HerbBlock) {
                    ((HerbBlock) herb).grow((ServerWorld) field_20373.getWorld(), field_20373.getRandom(), herbPos, herbBlock);
                    field_20373.getWorld().syncWorldEvent(WorldEvents.BEE_FERTILIZES_PLANT, herbPos, 15);
                }
            }
        }
    }
}
