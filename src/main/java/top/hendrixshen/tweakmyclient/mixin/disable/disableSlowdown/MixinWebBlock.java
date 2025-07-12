package top.hendrixshen.tweakmyclient.mixin.disable.disableSlowdown;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.WebBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.magiclib.libs.com.llamalad7.mixinextras.sugar.Local;
import top.hendrixshen.tweakmyclient.game.Configs;

@Mixin(WebBlock.class)
public class MixinWebBlock {
    @Inject(
            method = "entityInside",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Entity;makeStuckInBlock(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/phys/Vec3;)V"
            ),
            cancellable = true
    )
    private void onWalkInCobWebBlock(CallbackInfo ci, @Local Entity entity) {
        if ((Configs.disableSlowdown.getBooleanValue()) && entity instanceof LocalPlayer) {
            ci.cancel();
        }
    }
}
