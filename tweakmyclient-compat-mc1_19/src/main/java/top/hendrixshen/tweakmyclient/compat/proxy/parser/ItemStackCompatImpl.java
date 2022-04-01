package top.hendrixshen.tweakmyclient.compat.proxy.parser;

import com.mojang.brigadier.StringReader;
import net.minecraft.commands.arguments.item.ItemParser;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.hendrixshen.tweakmyclient.TweakMyClient;

public class ItemStackCompatImpl extends ItemStackCompatApi {
    public static void initCompat() {
        INSTANCE = new ItemStackCompatImpl();
    }

    @Override
    public ItemStack toItemStack(String string) {
        try {
            ItemParser.ItemResult result = ItemParser.parseForItem(new HolderLookup.RegistryLookup<>(Registry.ITEM), new StringReader(string));

            Item item = result.item().value();
            if (item != null) {
                ItemStack stack = new ItemStack(item);
                stack.setTag(result.nbt());
                return stack;
            }
        } catch (Exception e) {
            e.printStackTrace();
            TweakMyClient.getLogger().debug("Invalid item '{}'", string);
        }

        return ItemStack.EMPTY;
    }
}
