package top.hendrixshen.tweakmyclient.mixin.disable.disableAttackEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Registry;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.tweakmyclient.config.Configs;

@Mixin(Minecraft.class)
public class MixinMinecraft {
    @Shadow
    @Nullable
    public HitResult hitResult;

    @Shadow
    @Nullable
    public LocalPlayer player;

    @Inject(
            method = "startAttack",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;attack(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/entity/Entity;)V"
            ),
            cancellable = true
    )
    private void onAttack(CallbackInfo ci) {
        assert this.hitResult != null;
        Entity entity = ((EntityHitResult) this.hitResult).getEntity();
        String entityID = Registry.ENTITY_TYPE.getKey(entity.getType()).toString();
        String entityName = entity.getName().getString();
        if (Configs.disableAttackEntity.getBooleanValue() && Configs.listDisableAttackEntity.getStrings().stream().anyMatch(s -> entityID.contains(s) || entityName.contains(s))) {
            assert this.player != null;
            this.player.swing(InteractionHand.MAIN_HAND);
            ci.cancel();
        }
    }
}
