package io.github.mintynoura.mintyblends.mixin;

import io.github.mintynoura.mintyblends.block.HerbBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.bee.Bee;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.world.entity.animal.bee.Bee$BeeGrowCropGoal")
public abstract class BeeEntityMixin extends Goal {



    @Shadow
    @Final
    Bee this$0;

    @Inject(method = "tick()V", at = @At("TAIL"))
    private void mintyBlends$tickHerb(CallbackInfo info) {
        if (this$0.getRandom().nextInt(this.adjustedTickDelay(15)) == 0) {
            for (int i = 0; i <= 2; i++) {
                BlockPos herbPos = this$0.blockPosition().below(i);
                BlockState herbBlock = this$0.level().getBlockState(herbPos);
                Block herb = herbBlock.getBlock();
                if (herbBlock.is(BlockTags.BEE_GROWABLES) && herb instanceof HerbBlock) {
                    ((HerbBlock) herb).performBonemeal((ServerLevel) this$0.level(), this$0.getRandom(), herbPos, herbBlock);
                    this$0.level().levelEvent(LevelEvent.PARTICLES_BEE_GROWTH, herbPos, 15);
                }
            }
        }
    }
}
