package io.github.mintynoura.mintyblends.mixin;

import net.minecraft.world.inventory.RecipeBookType;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(RecipeBookType.class)
public enum RecipeBookTypeMixin {
    MINTYBLENDS_KETTLE_BREWING
}
