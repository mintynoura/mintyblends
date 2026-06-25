package io.github.mintynoura.mintyblends.mixin;

import io.github.mintynoura.mintyblends.networking.SendKettleRecipeBookValues;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.RecipeBook;
import net.minecraft.stats.ServerRecipeBook;
import net.minecraft.world.inventory.RecipeBookType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerRecipeBook.class)
public class ServerRecipeBookMixin extends RecipeBook {
    @Inject(method = "sendInitialRecipeBook", at = @At("TAIL"))
    private void mintyblends$sendKettleRecipeBook(ServerPlayer player, CallbackInfo ci) {
        ServerPlayNetworking.send(player, new SendKettleRecipeBookValues(getBookSettings().isOpen(RecipeBookType.MINTYBLENDS_KETTLE_BREWING), getBookSettings().isFiltering(RecipeBookType.MINTYBLENDS_KETTLE_BREWING)));
    }
}
