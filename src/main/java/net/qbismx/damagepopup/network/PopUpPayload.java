package net.qbismx.damagepopup.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.qbismx.damagepopup.DamagePopUp;

public record PopUpPayload(int entityId, float damage) implements CustomPacketPayload {

    // Payloadを用いて、サーバーからクライアントへデータを送る

    public static final Type<PopUpPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(DamagePopUp.MODID, "damage_popup"));

    public static final StreamCodec<FriendlyByteBuf, PopUpPayload> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.INT,
                    PopUpPayload::entityId,
                    ByteBufCodecs.FLOAT,
                    PopUpPayload::damage,
                    PopUpPayload::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }


}
