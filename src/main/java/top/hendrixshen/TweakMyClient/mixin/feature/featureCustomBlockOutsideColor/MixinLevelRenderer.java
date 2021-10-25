package top.hendrixshen.TweakMyClient.mixin.feature.featureCustomBlockOutsideColor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import fi.dy.masa.malilib.util.Color4f;
import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.TweakMyClient.TweakMyClient;
import top.hendrixshen.TweakMyClient.config.Configs;

@Mixin(LevelRenderer.class)
public abstract class MixinLevelRenderer {
    @Inject(
            method = "globalLevelEvent",
            at = @At(
                    value = "HEAD"
            )
    )
    private void onProcessGlobalEvent(int eventId, BlockPos pos, int i, CallbackInfo ci) {
        if (Configs.Feature.FEATURE_GLOBAL_EVENT_LISTENER.getBooleanValue()) {
            LocalPlayer player = TweakMyClient.getMinecraftClient().player;
            assert player != null;
            if (eventId == 1023) { //SoundEvents.ENTITY_WITHER_SPAWN
                player.displayClientMessage(new TextComponent(StringUtils.translate("tweakmyclient.message.globalEventListener.witherSpawn", pos.getX(), pos.getY(), pos.getZ())), false);
            } else if (eventId == 1038) { //SoundEvents.BLOCK_END_PORTAL_SPAWN
                player.displayClientMessage(new TextComponent(StringUtils.translate("tweakmyclient.message.globalEventListener.endPortalSpawn", pos.getX(), pos.getY(), pos.getZ())), false);
            } else if (eventId == 1028) { //SoundEvents.ENTITY_ENDER_DRAGON_DEATH
                player.displayClientMessage(new TextComponent(StringUtils.translate("tweakmyclient.message.globalEventListener.enderDragonDeath", pos.getX(), pos.getY(), pos.getZ())), false);
            }
        }
    }
}
