package io.github.mintynoura.mintyblends.util;

import net.minecraft.world.food.FoodProperties;

public class MintyBlendsFoods {
    public static final FoodProperties MINT = herbalLeaf();
    public static final FoodProperties CATNIP = herbalLeaf();
    public static final FoodProperties MEDICINAL_HERB = herbalLeaf();
    public static final FoodProperties CULINARY_HERB = new FoodProperties.Builder().nutrition(2).saturationModifier(0.2f).alwaysEdible().build();
    public static final FoodProperties SAGEBRUSH = herbalLeaf();
    public static final FoodProperties CUREFLOWER = netherFlower();
    public static final FoodProperties RENDFLOWER = netherFlower();
    public static final FoodProperties MINT_TEA = herbalTea();
    public static final FoodProperties GLOW_BERRY_TEA = berryTea();
    public static final FoodProperties SWEET_BERRY_TEA = berryTea();
    public static final FoodProperties WILDFLOWER_TEA = herbalTea();
    public static final FoodProperties TORCHFLOWER_TEA = herbalTea();
    public static final FoodProperties MINT_JELLY = new FoodProperties.Builder().nutrition(5).saturationModifier(0.4f).build();
    public static final FoodProperties BEETROOT_SALAD = new FoodProperties.Builder().nutrition(7).saturationModifier(0.7f).build();
    public static final FoodProperties PUMPKIN_STEW = new FoodProperties.Builder().nutrition(8).saturationModifier(0.8f).build();
    public static final FoodProperties STEAK_TARTARE = new FoodProperties.Builder().nutrition(6).saturationModifier(0.6f).build();

    public static FoodProperties herbalLeaf() {
        return new FoodProperties.Builder().nutrition(0).saturationModifier(0f).alwaysEdible().build();
    }
    public static FoodProperties herbalTea() {
        return new FoodProperties.Builder().nutrition(3).saturationModifier(0.3f).alwaysEdible().build();
    }
    public static FoodProperties berryTea() {
        return new FoodProperties.Builder().nutrition(5).saturationModifier(0.4f).alwaysEdible().build();
    }
    public static FoodProperties netherFlower() {
        return new FoodProperties.Builder().nutrition(1).saturationModifier(0.1f).alwaysEdible().build();
    }
}
