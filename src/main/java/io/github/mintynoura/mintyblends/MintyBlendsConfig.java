package io.github.mintynoura.mintyblends;
import folk.sisby.kaleido.api.ReflectiveConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.Comment;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.IntegerRange;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.SerializedName;
import folk.sisby.kaleido.lib.quiltconfig.api.values.TrackedValue;
import folk.sisby.kaleido.lib.quiltconfig.api.values.ValueMap;

public class MintyBlendsConfig extends ReflectiveConfig {
    @Comment("The amount of health to restore when applying the \"mintyblends:heal\" Herbal Effect (e.g. Medicinal Leaves)")
    @SerializedName("heal_amount")
    public final TrackedValue<Float> healAmount = this.value(2f);
    @Comment("The amount of hunger to fill when applying the \"mintyblends:feed\" Herbal Effect (e.g. Culinary Leaves)")
    @SerializedName("nutrition_amount")
    public final TrackedValue<Integer> nutritionAmount = this.value(2);
    @Comment("The amount of saturation to fill when applying the \"mintyblends:feed\" Herbal Effect (e.g. Culinary Leaves)")
    @SerializedName("saturation_modifier")
    public final TrackedValue<Float> saturationModifier = this.value(0.2f);

    @Comment("The cooldown timer in ticks for cat gifts after being fed Catnip")
    @SerializedName("cat_catnip_cooldown")
    @IntegerRange(min = 0, max = Integer.MAX_VALUE)
    public final TrackedValue<Integer> catCatnipCooldown = this.value(6000);
    @Comment("The cooldown timer in ticks for ocelot gifts after being fed Catnip")
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
        @Comment("The damage attribute increase for the Rending effect, per level")
        @SerializedName("rending_damage_modifier")
        public final TrackedValue<Float> rendingDamageModifier = this.value(0.2f);
        @Comment("The reduction in visibility range for the Stealth effect, per level")
        @SerializedName("rending_damage_modifier")
        public final TrackedValue<Float> stealthRangeModifier = this.value(0.2f);

        @Comment("A Map to use for status effect conversions, formatted as <\"key\" = \"value\"> pairs. The \"key\" is considered a positive effect, and the \"value\" is considered a negative effect")
        @SerializedName("status_effect_map")
        public final TrackedValue<ValueMap<String>> statusEffectMap = this.map("")
                .put("minecraft:haste", "minecraft:mining_fatigue")
                .put("minecraft:luck", "minecraft:unluck")
                .put("minecraft:regeneration", "minecraft:poison")
                .put("minecraft:resistance", "mintyblends:rending")
                .put("minecraft:speed", "minecraft:slowness")
                .put("minecraft:strength", "minecraft:weakness")
                .build();
    }
}
