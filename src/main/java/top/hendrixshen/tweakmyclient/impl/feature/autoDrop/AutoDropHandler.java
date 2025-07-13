package top.hendrixshen.tweakmyclient.impl.feature.autoDrop;

import lombok.Getter;
import lombok.NoArgsConstructor;
import top.hendrixshen.magiclib.api.compat.minecraft.world.entity.player.PlayerCompat;
import top.hendrixshen.tweakmyclient.api.event.LocalPlayerListener;
import top.hendrixshen.tweakmyclient.game.Configs;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class AutoDropHandler implements LocalPlayerListener {
    @Getter(lazy = true)
    private static final AutoDropHandler instance = new AutoDropHandler();

    private int autoDropTimer = 0;

    @Override
    public void onGameJoin(LocalPlayer localPlayer) {
        // NO-OP
    }

    @Override
    public void onTick(LocalPlayer localPlayer) {
        if (!Configs.autoDrop.getBooleanValue()) {
            return;
        }

        if (this.autoDropTimer > 0) {
            this.autoDropTimer--;
            return;
        }

        Minecraft mc = Minecraft.getInstance();

        if (mc.screen instanceof AbstractContainerScreen && !(mc.screen instanceof InventoryScreen)) {
            return;
        }

        MultiPlayerGameMode multiPlayerGameMode = mc.gameMode;

        if (localPlayer == null || multiPlayerGameMode == null) {
            return;
        }

        PlayerCompat playerCompat = PlayerCompat.of(localPlayer);

        for (int slot = 9; slot < 45; slot++) {
            int adjustedSlot = slot;

            if (adjustedSlot >= 36) {
                adjustedSlot -= 36;
            }

            ItemStack stack = playerCompat.getInventory().getItem(adjustedSlot);

            if (stack.isEmpty()) {
                continue;
            }

            if (Configs.autoDropRestriction.isAllowed(stack)) {
                multiPlayerGameMode.handleInventoryMouseClick(0, slot, 1, ClickType.THROW, localPlayer);
            }
        }

        this.autoDropTimer = Configs.autoDropInterval.getIntegerValue();
    }
}
