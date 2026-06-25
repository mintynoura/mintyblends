package io.github.mintynoura.mintyblends.mixin;

import com.mojang.serialization.MapCodec;
import net.minecraft.stats.RecipeBookSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(RecipeBookSettings.TypeSettings.class)
public interface RecipeBookTypeSettingsInvoker {
    @Invoker("codec")
    static MapCodec<RecipeBookSettings.TypeSettings> mintyblends$invokeCodec(String openField, String filteringField) {
        throw new RuntimeException("");
    }
}
