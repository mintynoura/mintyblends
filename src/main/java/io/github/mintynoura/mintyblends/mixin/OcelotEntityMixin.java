package io.github.mintynoura.mintyblends.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.mintynoura.mintyblends.util.ModTags;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.TagKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(OcelotEntity.class)
public class OcelotEntityMixin {

    @WrapOperation(method = "method_58370", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isIn(Lnet/minecraft/registry/tag/TagKey;)Z"))
    private static boolean addCatnipToGoal(ItemStack instance, TagKey<Item> tag, Operation<Boolean> original) {
        return original.call(instance, tag) || instance.isIn(ModTags.Items.CAT_LOVED);
    }
}
