package top.hendrixshen.tweakmyclient.mixin.feature.attackEntityRestriction;

import top.hendrixshen.tweakmyclient.game.Configs;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {
    @WrapWithCondition(
            method = "startAttack",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;attack(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/entity/Entity;)V"
            )
    )
    private boolean onStartAttack(MultiPlayerGameMode instance, Player player, Entity entity) {
        if (!Configs.attackEntityRestriction.getBooleanValue()) {
            return true;
        }

        return Configs.attackEntityRestrictionList.isAllowed(entity);
    }
}
