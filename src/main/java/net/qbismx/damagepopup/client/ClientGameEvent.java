package net.qbismx.damagepopup.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLivingEvent;
import net.qbismx.damagepopup.DamagePopUp;
import net.qbismx.damagepopup.popup.PopUp;
import net.qbismx.damagepopup.popup.PopUpManager;
import org.joml.Quaternionf;

import java.util.Iterator;
import java.util.List;

@EventBusSubscriber(modid = DamagePopUp.MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class ClientGameEvent {


    @SubscribeEvent
    public static void render(RenderLivingEvent.Post<?, ?> event) {
        LivingEntity entity = event.getEntity();

        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null) return;
        // if (!entity.hasLineOfSight(player)) return; これで軽めにしたかったが、逆に重くなるらしい。
        if (entity.distanceToSqr(player) > 400) return;

       List<PopUp> list = PopUpManager.get(entity.getId());
       if (list == null) {return;}

        PoseStack poseStack = event.getPoseStack();
        MultiBufferSource buffer = event.getMultiBufferSource();

        Iterator<PopUp> it = list.iterator();

        poseStack.pushPose();

        Quaternionf cameraRot = mc.getEntityRenderDispatcher().cameraOrientation();

        while(it.hasNext()) {

            PopUp popUp = it.next();

            popUp.age++;

            if(popUp.age > 40){
                it.remove();
                continue;
            }

            Vec3 cameraPos = mc.gameRenderer.getMainCamera().getPosition();
            Vec3 dir = entity.position().subtract(cameraPos).normalize();
            Vec3 offset = dir.scale(-1);

            poseStack.translate(
                    offset.x + popUp.randX,
                    entity.getBbHeight() * 0.6 + popUp.age * 0.03,
                    offset.z + popUp.randZ
            );


            poseStack.mulPose(cameraRot);
            poseStack.mulPose(Axis.YP.rotationDegrees(180)); // scaleで文字が反転するので、これで予め裏返しておかないと見えなくなる

            poseStack.scale(-0.05f, -0.05f, 0.05f);

            String text = String.valueOf((int) popUp.damage);

            mc.font.drawInBatch(
                    text,
                    -mc.font.width(text) / 2f,
                    0,
                    0xFF5555,
                    false,
                    poseStack.last().pose(),
                    buffer,
                    Font.DisplayMode.NORMAL,
                    OverlayTexture.NO_OVERLAY,
                    15728880
            );

        }

        poseStack.popPose();

        if(list.isEmpty()){
            PopUpManager.remove(entity.getId());
        }

    }

}
