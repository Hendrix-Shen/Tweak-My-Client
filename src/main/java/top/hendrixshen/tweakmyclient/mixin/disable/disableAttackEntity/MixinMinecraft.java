package top.hendrixshen.tweakmyclient.mixin.disable.disableAttackEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
//#if MC >= 11903
import net.minecraft.core.registries.BuiltInRegistries;
//#else
//$$ import net.minecraft.core.Registry;
//#endif
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
//#if MC >= 11800
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//#else
//$$ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//#endif
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
    //#if MC >= 11800
    private void onStartAttack(CallbackInfoReturnable<Boolean> cir) {
    //#else
    //$$ private void onStartAttack(CallbackInfo cir) {
    //#endif
        if (this.hitResult != null && this.player != null) {
            Entity entity = ((EntityHitResult) hitResult).getEntity();
            //#if MC >= 11903
            String entityID = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString();
            //#else
            //$$ String entityID = Registry.ENTITY_TYPE.getKey(entity.getType()).toString();
            //#endif
            String entityName = entity.getName().getString();
            if (Configs.disableAttackEntity && Configs.listDisableAttackEntity.stream().anyMatch(s -> entityID.contains(s) || entityName.contains(s))) {
                player.swing(InteractionHand.MAIN_HAND);
                cir.cancel();
            }
        }
    }
}
