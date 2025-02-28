package top.hendrixshen.tweakmyclient.impl.config;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fi.dy.masa.malilib.util.restrictions.UsageRestriction;
import net.minecraft.commands.arguments.item.ItemParser;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.hendrixshen.magiclib.api.compat.minecraft.world.item.ItemStackCompat;
import top.hendrixshen.tweakmyclient.SharedConstants;

import java.util.List;
import java.util.Set;

//#if MC > 11902
//$$ import net.minecraft.core.registries.BuiltInRegistries;
//#elseif MC > 11802
//$$ import net.minecraft.core.HolderLookup;
//$$ import net.minecraft.core.Registry;
//#endif

public class ItemStackRestriction extends UsageRestriction<ItemStack> {
    @Override
    protected void setValuesForList(Set<ItemStack> set, List<String> nbtList) {
        for (String nbt : nbtList) {
            ItemStack stack = ItemStackRestriction.toItemStack(nbt);

            if (!stack.isEmpty()) {
                set.add(stack);
            }
        }
    }

    @Override
    public boolean isAllowed(ItemStack value) {
        if (value.hasTag()) {
            if (this.type == UsageRestriction.ListType.BLACKLIST) {
                return this.blackList.isEmpty()
                        || this.blackList.stream().noneMatch(itemStack -> ItemStackRestriction.matchItems(itemStack, value));
            }

            return this.type == UsageRestriction.ListType.NONE ||
                    this.whiteList.stream().anyMatch(itemStack -> ItemStackRestriction.matchItems(itemStack, value));
        }

        return super.isAllowed(value);
    }

    public static boolean matchItems(ItemStack stackA, ItemStack stackB) {
        if (stackA.hasTag()) {
            return stackA.isDamageableItem()
                    ? ItemStackCompat.isSameItemSameTags(stackA, stackB)
                    : ItemStackCompat.isSameItemSameTagsIgnoreDurability(stackA, stackB);
        }

        return ItemStackCompat.isSame(stackA, stackB);
    }

    public static ItemStack toItemStack(String string) {
        try {
            //#if MC > 11802
            //$$ ItemParser.ItemResult result = ItemParser.parseForItem(
            //#if MC > 11902
            //$$         BuiltInRegistries.ITEM.asLookup(),
            //#else
            //$$         new HolderLookup.RegistryLookup<>(Registry.ITEM),
            //#endif
            //$$         new StringReader(string)
            //$$ );
            //$$ Item item = result.item().value();
            //#else
            ItemParser reader = new ItemParser(new StringReader(string), true);
            reader.parse();
            Item item = reader.getItem();
            //#endif

            if (item != null) {
                ItemStack stack = new ItemStack(item);
                stack.setTag(
                        //#if MC > 11802
                        //$$ result.nbt()
                        //#else
                        reader.getNbt()
                        //#endif
                );

                return stack;
            }
        } catch (CommandSyntaxException e) {
            SharedConstants.getLogger().error("Invalid item '{}'", string);
        }

        return ItemStack.EMPTY;
    }
}
