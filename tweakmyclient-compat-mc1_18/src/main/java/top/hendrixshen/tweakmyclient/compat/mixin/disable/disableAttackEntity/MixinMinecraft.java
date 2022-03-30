package top.hendrixshen.tweakmyclient.compat.mixin.disable.disableAttackEntity;

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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.hendrixshen.magiclib.dependency.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.annotation.Dependency;
import top.hendrixshen.tweakmyclient.config.Configs;
import top.hendrixshen.tweakmyclient.helper.CommonCompatLib;

@Dependencies(not = @Dependency(value = "minecraft", versionPredicate = "<1.18"))
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
    private void onAttack(CallbackInfoReturnable<Boolean> cir) {
        if (CommonCompatLib.disableAttackEntity(this.hitResult, this.player)) {
            cir.cancel();
        }
    }
}
