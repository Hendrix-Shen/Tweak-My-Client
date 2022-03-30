package top.hendrixshen.tweakmyclient.compat.proxy.parser;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.arguments.item.ItemParser;
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
}
