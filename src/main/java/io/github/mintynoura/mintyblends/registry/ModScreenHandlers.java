package io.github.mintynoura.mintyblends.registry;

import io.github.mintynoura.mintyblends.MintyBlends;
import io.github.mintynoura.mintyblends.screen.KettleScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.inventory.MenuType;

public class ModScreenHandlers {
    public static final MenuType<KettleScreenHandler> KETTLE_SCREEN_HANDLER = Registry.register(BuiltInRegistries.MENU, Identifier.fromNamespaceAndPath(MintyBlends.MOD_ID, "kettle_screen_handler"), new ExtendedScreenHandlerType<>(KettleScreenHandler::new, BlockPos.STREAM_CODEC));


    public static void registerScreenHandlers() {}
}
