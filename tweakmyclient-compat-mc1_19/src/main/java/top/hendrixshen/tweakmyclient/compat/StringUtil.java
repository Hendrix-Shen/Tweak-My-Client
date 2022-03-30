package top.hendrixshen.tweakmyclient.compat;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.arguments.item.ItemParser;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.hendrixshen.tweakmyclient.TweakMyClient;

import java.util.HashSet;
import java.util.List;

public class StringUtil {
    public static ItemStack parseItemFromString(String str) {
        try {
            ItemParser.ItemResult result = ItemParser.parseForItem(new HolderLookup.RegistryLookup<>(Registry.ITEM), new StringReader(str));

            Item item = result.item().value();
            if (item != null) {
                ItemStack stack = new ItemStack(item);
                stack.setTag(result.nbt());
                return stack;
            }
        } catch (CommandSyntaxException e) {
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
