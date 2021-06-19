package top.hendrixshen.TweakMyClient.util;

import com.mojang.brigadier.StringReader;
import net.minecraft.command.argument.ItemStringReader;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import top.hendrixshen.TweakMyClient.TweakMyClient;

import java.util.HashSet;
import java.util.List;

public class StringUtils {
    public static ItemStack parseItemFromString(String str) {
        try {
            ItemStringReader reader = new ItemStringReader(new StringReader(str), true);
            reader.consume();
            Item item = reader.getItem();

            if (item != null) {
                ItemStack stack = new ItemStack(item);
                stack.setTag(reader.getTag());
                return stack;
            }
        } catch (Exception e) {
            e.printStackTrace();
            TweakMyClient.logger.warn("Invalid item '{}'", str);
        }

        return ItemStack.EMPTY;
    }

    public static HashSet<Item> getItemStackSets(List<String> items) {
        HashSet<Item> itemStackSet = new HashSet<>();
        for (String str : items) {
            ItemStack stack = parseItemFromString(str);

            if (!stack.isEmpty()) {
                itemStackSet.add(stack.getItem());
            }
        }
        return itemStackSet;
    }
}
