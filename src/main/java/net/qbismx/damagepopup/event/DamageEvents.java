package net.qbismx.damagepopup.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.qbismx.damagepopup.DamagePopUp;
import net.qbismx.damagepopup.network.ModNetwork;
import net.qbismx.damagepopup.network.PopUpPayload;

@EventBusSubscriber(modid = DamagePopUp.MODID, bus = EventBusSubscriber.Bus.GAME)
public class DamageEvents {

    @SubscribeEvent
    public static void onDamage(LivingDamageEvent.Post event){

        LivingEntity entity = event.getEntity();

        if (entity.level().isClientSide) return; // クライアントでの処理は扱わない
        // ここからサーバー処理
        float damage = event.getNewDamage();
        if (damage < 0) return;
        PopUpPayload payload = new PopUpPayload(entity.getId(), damage);

        PacketDistributor.sendToPlayersTrackingEntity(entity, payload); // 送信

    }

}
