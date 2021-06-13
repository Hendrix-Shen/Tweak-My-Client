package top.hendrixshen.TweakMyClient.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.SlotActionType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import top.hendrixshen.TweakMyClient.interfaces.IClientPlayerInteractionManager;

@Mixin(ClientPlayerInteractionManager.class)
public abstract class MixinClientPlayerInteractionManager implements IClientPlayerInteractionManager {
    @Shadow
    public abstract ItemStack clickSlot(int int_1, int int_2, int int_3, SlotActionType slotActionType_1, PlayerEntity playerEntity_1);

    @Shadow @Final private MinecraftClient client;

    @Override
    public ItemStack windowClickThrow(int slot)
    {
        return clickSlot(0, slot, 1, SlotActionType.THROW, client.player);
    }
}
