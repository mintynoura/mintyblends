package io.github.mintynoura.mintyblends.registry;

import io.github.mintynoura.mintyblends.MintyBlends;
import io.github.mintynoura.mintyblends.screen.KettleScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class ModScreenHandlers {
    public static final ScreenHandlerType<KettleScreenHandler> KETTLE_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER, Identifier.of(MintyBlends.MOD_ID, "kettle_screen_handler"), new ExtendedScreenHandlerType<>(KettleScreenHandler::new, BlockPos.PACKET_CODEC));


    public static void registerScreenHandlers() {}
}
