package top.hendrixshen.tweakmyclient.util;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.hendrixshen.tweakmyclient.compat.proxy.parser.ItemStackCompatApi;

import java.util.HashSet;
import java.util.List;

public class StringUtil {
    public static HashSet<Item> getItemStackSets(List<String> items) {
        HashSet<Item> itemStackSet = new HashSet<>();
        for (String str : items) {
            ItemStack stack = ItemStackCompatApi.getInstance().toItemStack(str);

            if (!stack.isEmpty()) {
                itemStackSet.add(stack.getItem());
            }
        }
        return itemStackSet;
    }
}
