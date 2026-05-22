package io.github.mintynoura.mintyblends;
import folk.sisby.kaleido.api.ReflectiveConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.Comment;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.FloatRange;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.IntegerRange;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.SerializedName;
import folk.sisby.kaleido.lib.quiltconfig.api.values.TrackedValue;
import folk.sisby.kaleido.lib.quiltconfig.api.values.ValueMap;

public class MintyBlendsConfig extends ReflectiveConfig {
    @Comment("The amount of time (in ticks) that blends should take to brew in a Kettle. Does not affect recipes")
    @SerializedName("kettle_blend_brewing_time")
    @IntegerRange(min = 1, max = Integer.MAX_VALUE)
    public final TrackedValue<Integer> kettleBlendBrewingTime = this.value(400);
    @Comment("The modifier Sugar applies to potion effect durations when used as a blending ingredient")
    @SerializedName("sugar_duration_modifier")
    @FloatRange(min = 0.1f, max = 10.0f)
    public final TrackedValue<Float> sugarDurationModifier = this.value(1.5f);
    @Comment("Whether or not to increase the default Potion stack size")
    @SerializedName("modify_potion_stack_size")
    public final TrackedValue<Boolean> modifyPotionStackSize = this.value(true);
    @Comment("The max stack size for Potions, only applied when \"modify_potion_stack_size\" is enabled")
    @SerializedName("potion_stack_size")
    @IntegerRange(min = 1, max = 99)
    public final TrackedValue<Integer> potionStackSize = this.value(16);
    @Comment("The cooldown timer in ticks for Cat gifts after being fed Catnip")
    @SerializedName("cat_catnip_cooldown")
    @IntegerRange(min = 0, max = Integer.MAX_VALUE)
    public final TrackedValue<Integer> catCatnipCooldown = this.value(6000);
    @Comment("The cooldown timer in ticks for Ocelot gifts after being fed Catnip")
    @SerializedName("ocelot_catnip_cooldown")
    @IntegerRange(min = 0, max = Integer.MAX_VALUE)
    public final TrackedValue<Integer> ocelotCatnipCooldown = this.value(6000);

    @SerializedName("StatusEffects")
    public final StatusEffectSection statusEffectSection = new StatusEffectSection();
    public static final class StatusEffectSection extends Section {
        @Comment("The block reach attribute increase for the Reaching effect, per level")
        @SerializedName("reaching_block_range_increase")
        public final TrackedValue<Float> reachingBlockRangeModifier = this.value(2f);
        @Comment("The entity reach attribute increase for the Reaching effect, per level")
        @SerializedName("reaching_entity_range_increase")
        public final TrackedValue<Float> reachingEntityRangeModifier = this.value(2f);
        @Comment("The damage increase multiplier for the Rending effect, per level")
        @SerializedName("rending_damage_modifier")
        public final TrackedValue<Float> rendingDamageModifier = this.value(0.2f);
        @Comment("The reduction in visibility range for the Stealth effect, per level")
        @SerializedName("rending_damage_modifier")
        public final TrackedValue<Float> stealthRangeModifier = this.value(0.2f);
        @Comment("The increase multiplier in gravity for the Fast Falling effect, per level")
        @SerializedName("fast_falling_gravity_modifier")
        public final TrackedValue<Float> fastFallingGravityModifier = this.value(0.5f);
        @Comment("The increase multiplier in fall damage for the Fast Falling effect, per level")
        @SerializedName("fast_falling_damage_modifier")
        public final TrackedValue<Float> fastFallingFallDamageModifier = this.value(0.5f);

        @Comment("A Map to use for status effect conversions, formatted as <\"key\" = \"value\"> pairs. The \"key\" is considered a positive effect, and the \"value\" is considered a negative effect")
        @SerializedName("status_effect_map")
        public final TrackedValue<ValueMap<String>> statusEffectMap = this.map("")
                .put("minecraft:haste", "minecraft:mining_fatigue")
                .put("minecraft:luck", "minecraft:unluck")
                .put("minecraft:regeneration", "minecraft:poison")
                .put("minecraft:resistance", "mintyblends:rending")
                .put("minecraft:speed", "minecraft:slowness")
                .put("minecraft:strength", "minecraft:weakness")
                .put("minecraft:invisibility", "minecraft:glowing")
                .put("minecraft:night_vision", "minecraft:blindness")
                .put("minecraft:slow_falling", "mintyblends:fast_falling")
                .build();
    }
}
