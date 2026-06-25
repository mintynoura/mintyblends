package io.github.mintynoura.mintyblends.networking;

import io.github.mintynoura.mintyblends.MintyBlends;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record SendKettleRecipeBookValues(boolean open, boolean filtering) implements CustomPacketPayload {
    public static final Identifier ID = Identifier.fromNamespaceAndPath(MintyBlends.ID, "send_kettle_recipe_book_values");
    public static final Type<SendKettleRecipeBookValues> TYPE = new Type<>(ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, SendKettleRecipeBookValues> STREAM_CODEC = StreamCodec.of(SendKettleRecipeBookValues::encode, SendKettleRecipeBookValues::new);

    public SendKettleRecipeBookValues(FriendlyByteBuf buf) {
        this(buf.readBoolean(), buf.readBoolean());
    }

    public static void encode(FriendlyByteBuf buf, SendKettleRecipeBookValues packet) {
        buf.writeBoolean(packet.open);
        buf.writeBoolean(packet.filtering);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
