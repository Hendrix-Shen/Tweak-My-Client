package top.hendrixshen.tweakmyclient.util;

import com.mojang.brigadier.StringReader;
import net.minecraft.commands.arguments.item.ItemParser;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.hendrixshen.tweakmyclient.TweakMyClient;

import java.util.HashSet;
import java.util.List;

public class StringUtil {
    public static ItemStack parseItemFromString(String str) {
        try {
            ItemParser reader = new ItemParser(new StringReader(str), true);
            reader.parse();
            Item item = reader.getItem();

            if (item != null) {
                ItemStack stack = new ItemStack(item);
                stack.setTag(reader.getNbt());
                return stack;
            }
        } catch (Exception e) {
            e.printStackTrace();
            TweakMyClient.getLogger().debug("Invalid item '{}'", str);
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
