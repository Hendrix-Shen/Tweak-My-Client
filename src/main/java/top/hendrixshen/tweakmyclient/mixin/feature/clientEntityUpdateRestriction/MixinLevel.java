package top.hendrixshen.tweakmyclient.mixin.feature.clientEntityUpdateRestriction;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.tweakmyclient.game.Configs;

import java.util.function.Consumer;

@Mixin(Level.class)
public abstract class MixinLevel {
    @Inject(method = "guardEntityTick", at = @At("HEAD"), cancellable = true)
    private void onGuardEntityTick(Consumer<Entity> consumer, Entity entity, CallbackInfo ci) {
        if (!Configs.clientEntityUpdateRestriction.getBooleanValue() ||
                entity instanceof Player ||
                Configs.clientEntityUpdateRestrictionList.isAllowed(entity)) {
            return;
        }

        ci.cancel();
    }
}
