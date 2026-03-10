package net.qbismx.damagepopup.network;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.qbismx.damagepopup.DamagePopUp;
import net.qbismx.damagepopup.popup.PopUpManager;

@EventBusSubscriber(modid = DamagePopUp.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModNetwork {
    // ネットワークの登録

    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event){

        final PayloadRegistrar register = event.registrar("1");

        register.playToClient(
                PopUpPayload.TYPE,
                PopUpPayload.STREAM_CODEC,
                (payload, context) -> {

                    context.enqueueWork(() -> {

                       // System.out.println("Packet received: " + payload.damage()); // 動作検証用。機能している。

                        // Level level = context.player().level();

                        Entity entity = context.player().level().getEntity(payload.entityId());
                        // Entity entity = level.getEntity(payload.entityId());

                        if (entity instanceof LivingEntity living) {
                            PopUpManager.add(living.getId(), payload.damage());
                        }
                    });
                }
        );

    }

}
