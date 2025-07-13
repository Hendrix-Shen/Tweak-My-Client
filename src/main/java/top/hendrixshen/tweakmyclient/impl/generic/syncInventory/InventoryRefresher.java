package top.hendrixshen.tweakmyclient.impl.generic.syncInventory;

// CHECKSTYLE.OFF: ImportOrder
//#if MC > 11605
//$$ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
//#endif
// CHECKSTYLE.ON: ImportOrder

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ServerboundContainerClickPacket;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

// CHECKSTYLE.OFF: ImportOrder
//#if MC >= 12105
//$$ import net.minecraft.network.HashedStack;
//#endif

//#if MC >= 12006
//$$ import net.minecraft.core.component.DataComponents;
//$$ import net.minecraft.nbt.CompoundTag;
//$$ import net.minecraft.world.item.component.CustomData;
//#endif
// CHECKSTYLE.ON: ImportOrder

public class InventoryRefresher {
    public static void refresh() {
        Minecraft mc = Minecraft.getInstance();
        ClientPacketListener clientPacketListener = mc.getConnection();

        if (mc.player == null || clientPacketListener == null) {
            return;
        }

        ItemStack dummyItem = new ItemStack(Items.BEDROCK);
        //#if MC >= 12006
        //$$ CompoundTag compoundTag = new CompoundTag();
        //$$ compoundTag.putDouble("dummy", Double.NaN);
        //$$ CustomData.set(DataComponents.CUSTOM_DATA, dummyItem, compoundTag);
        //#else
        dummyItem.getOrCreateTag().putDouble("dummy", Double.NaN);
        //#endif
        AbstractContainerMenu container = mc.player.containerMenu;
        //#if MC >= 12105
        //$$ HashedStack itemStackHash = HashedStack.create(dummyItem, clientPacketListener.decoratedHashOpsGenenerator());
        //#endif

        clientPacketListener.send(new ServerboundContainerClickPacket(
                // CHECKSTYLE.OFF: NoWhitespaceBefore
                // CHECKSTYLE.OFF: SeparatorWrap
                container.containerId,
                //#if MC > 11605
                //$$ container.getStateId(),
                //#endif
                (short) -99,
                (byte) 2,
                ClickType.QUICK_MOVE,
                //#if MC < 12105
                dummyItem,
                //#endif
                //#if MC >= 11700
                //$$ new Int2ObjectOpenHashMap<>()
                //#if MC >= 12105
                //$$ , itemStackHash
                //#endif
                //#else
                container.backup(mc.player.inventory)
                //#endif
                // CHECKSTYLE.ON: SeparatorWrap
                // CHECKSTYLE.ON: NoWhitespaceBefore
        ));
    }
}
