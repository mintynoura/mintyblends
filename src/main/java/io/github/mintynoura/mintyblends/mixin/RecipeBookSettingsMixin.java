package io.github.mintynoura.mintyblends.mixin;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.stats.RecipeBookSettings;
import net.minecraft.world.inventory.RecipeBookType;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Function;
import java.util.function.UnaryOperator;

@Mixin(RecipeBookSettings.class)
public class RecipeBookSettingsMixin {
    @Shadow
    @Final
    @Mutable
    public static MapCodec<RecipeBookSettings> MAP_CODEC;
    @Unique
    private RecipeBookSettings.TypeSettings mintyBlends$kettleBrewing = RecipeBookSettings.TypeSettings.DEFAULT;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void mintyBlends$modifyCodec(CallbackInfo ci) {
        MAP_CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
                MAP_CODEC.forGetter(Function.identity()),
                RecipeBookTypeSettingsInvoker.mintyblends$invokeCodec("IsMintyBlendsKettleOpen", "IsMintyBlendsKettleFilteringCraftable").forGetter(recipeBookSettings -> ((RecipeBookSettingsMixin)(Object) recipeBookSettings).mintyBlends$kettleBrewing))
                .apply(i, ((recipeBookSettings, typeSettings) -> {
                    ((RecipeBookSettingsMixin)(Object) recipeBookSettings).mintyBlends$kettleBrewing = typeSettings;
                    return recipeBookSettings;
                })
        ));
    }

    @Inject(method = "replaceFrom", at = @At("TAIL"))
    private void mintyblends$replaceFromKettle(RecipeBookSettings other, CallbackInfo ci) {
        this.mintyBlends$kettleBrewing = ((RecipeBookSettingsMixin)(Object) other).mintyBlends$kettleBrewing;
    }

    @Inject(method = "getSettings", at = @At("HEAD"), cancellable = true)
    private void mintyBlends$getSettingsKettle(RecipeBookType type, CallbackInfoReturnable<RecipeBookSettings.TypeSettings> cir) {
        if (type == RecipeBookType.MINTYBLENDS_KETTLE_BREWING) {
            cir.setReturnValue(this.mintyBlends$kettleBrewing);
        }
    }

    @Inject(method = "updateSettings", at = @At("HEAD"), cancellable = true)
    private void mintyBlends$updateSettingsKettle(RecipeBookType recipeBookType, UnaryOperator<RecipeBookSettings.TypeSettings> operator, CallbackInfo ci) {
        if (recipeBookType == RecipeBookType.MINTYBLENDS_KETTLE_BREWING) {
            this.mintyBlends$kettleBrewing = operator.apply(this.mintyBlends$kettleBrewing);
            ci.cancel();
        }
    }
}
