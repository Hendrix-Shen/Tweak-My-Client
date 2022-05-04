package top.hendrixshen.tweakmyclient.util;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.arguments.item.ItemParser;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.hendrixshen.magiclib.language.I18n;
import top.hendrixshen.tweakmyclient.TweakMyClient;
import top.hendrixshen.tweakmyclient.TweakMyClientReference;

import java.util.HashSet;
import java.util.List;

public class StringUtil {
    public static HashSet<Item> getItemStackSets(List<String> items) {
        HashSet<Item> itemStackSet = new HashSet<>();
        for (String str : items) {
            ItemStack stack = StringUtil.toItemStack(str);

            if (!stack.isEmpty()) {
                itemStackSet.add(stack.getItem());
            }
        }
        return itemStackSet;
    }

    public static ItemStack toItemStack(String string) {
        try {
            ItemParser reader = new ItemParser(new StringReader(string), true);
            reader.parse();
            Item item = reader.getItem();

            if (item != null) {
                ItemStack stack = new ItemStack(item);
                stack.setTag(reader.getNbt());
                return stack;
            }
        } catch (CommandSyntaxException e) {
            TweakMyClient.getLogger().debug("Invalid item '{}'", string);
        }

        return ItemStack.EMPTY;
    }

    public static String tr(String key, Object... objects) {
        return I18n.get(String.format("%s.%s", TweakMyClientReference.getModId(), key), objects);
    }
}
