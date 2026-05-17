package io.github.mintynoura.mintyblends.registry;

import io.github.mintynoura.mintyblends.MintyBlends;
import io.github.mintynoura.mintyblends.screen.KettleMenu;
import net.fabricmc.fabric.api.menu.v1.ExtendedMenuType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.inventory.MenuType;

public class MintyBlendsMenus {
    public static final MenuType<KettleMenu> KETTLE_MENU = Registry.register(BuiltInRegistries.MENU, Identifier.fromNamespaceAndPath(MintyBlends.MOD_ID, "kettle_screen_handler"), new ExtendedMenuType<>(KettleMenu::new, BlockPos.STREAM_CODEC));


    public static void initialize() {}
}
