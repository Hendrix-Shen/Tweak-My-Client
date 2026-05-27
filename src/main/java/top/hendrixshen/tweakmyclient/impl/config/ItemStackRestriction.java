package top.hendrixshen.tweakmyclient.impl.config;

import fi.dy.masa.malilib.util.restrictions.UsageRestriction;
import lombok.EqualsAndHashCode;
import top.hendrixshen.magiclib.api.compat.minecraft.world.item.ItemStackCompat;
import top.hendrixshen.tweakmyclient.SharedConstants;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.arguments.item.ItemParser;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

// CHECKSTYLE.OFF: ImportOrder
//#if MC >= 26.1
//$$ import net.minecraft.commands.arguments.item.ItemInput;
//#elseif MC > 12004
//$$ import net.minecraft.commands.arguments.item.ItemParser.ItemResult;
//#elseif MC > 11902
//$$ import net.minecraft.core.registries.BuiltInRegistries;
//#elseif MC > 11802
//$$ import net.minecraft.core.HolderLookup;
//$$ import net.minecraft.core.Registry;
//#endif
// CHECKSTYLE.ON: ImportOrder

import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = false)
public class ItemStackRestriction extends UsageRestriction<ItemStack> {
    private boolean shouldReParse = false;
    private List<String> namesBlacklist = null;
    private List<String> namesWhitelist = null;

    public ItemStackRestriction() {
        super();
        InGameParserHandler.getInstance().registerItemStackRestriction(this);
    }

    @Override
    public void setListContents(List<String> namesBlacklist, List<String> namesWhitelist) {
        if (Minecraft.getInstance().level == null) {
            this.shouldReParse = true;
            this.namesBlacklist = namesBlacklist;
            this.namesWhitelist = namesWhitelist;
            return;
        }

        this.shouldReParse = false;
        this.namesBlacklist = null;
        this.namesWhitelist = null;
        super.setListContents(namesBlacklist, namesWhitelist);
    }

    @Override
    protected void setValuesForList(Set<ItemStack> set, List<String> nbtList) {
        for (String nbt : nbtList) {
            ItemStack stack = this.toItemStack(nbt);

            if (!stack.isEmpty()) {
                set.add(stack);
            }
        }
    }

    @Override
    public boolean isAllowed(ItemStack value) {
        if (
                // CHECKSTYLE.OFF: Indentation
                // @formatter:off
                //#if MC > 12004
                //$$ value.getComponents().isEmpty()
                //#else
                value.hasTag()
                //#endif
                // @formatter:on
                // CHECKSTYLE.OFF: Indentation
        ) {
            if (this.type == UsageRestriction.ListType.BLACKLIST) {
                return this.blackList.isEmpty()
                        || this.blackList.stream().noneMatch(itemStack -> this.matchItems(itemStack, value));
            }

            return this.type == UsageRestriction.ListType.NONE
                    || this.whiteList.stream().anyMatch(itemStack -> this.matchItems(itemStack, value));
        }

        return super.isAllowed(value);
    }

    private boolean matchItems(ItemStack stackA, ItemStack stackB) {
        if (
                // CHECKSTYLE.OFF: Indentation
                // @formatter:off
                //#if MC > 12004
                //$$ stackA.getComponents().isEmpty()
                //#else
                stackA.hasTag()
                //#endif
                // @formatter:on
                // CHECKSTYLE.OFF: Indentation
        ) {
            return stackA.isDamageableItem()
                    ? ItemStackCompat.isSameItemSameTags(stackA, stackB)
                    : ItemStackCompat.isSameItemSameTagsIgnoreDurability(stackA, stackB);
        }

        return ItemStackCompat.isSame(stackA, stackB);
    }

    private ItemStack toItemStack(String string) {
        try {
            //#if MC > 11802
            //#if MC > 12004
            //$$ Minecraft mc = Minecraft.getInstance();
            //$$
            //$$ if (mc.level == null) {
            //$$     return ItemStack.EMPTY;
            //$$ }
            //$$
            //$$ ItemParser itemParser = new ItemParser(Minecraft.getInstance().level.registryAccess());
            //#if MC >= 26.1
            //$$ ItemInput result = itemParser.parse(new StringReader(string));
            //#else
            //$$ ItemResult result = itemParser.parse(new StringReader(string));
            //#endif
            //#else
            //$$ ItemParser.ItemResult result = ItemParser.parseForItem(
            //#if MC > 11902
            //$$         BuiltInRegistries.ITEM.asLookup(),
            //#else
            //$$         new HolderLookup.RegistryLookup<>(Registry.ITEM),
            //#endif
            //$$         new StringReader(string)
            //$$ );
            //#endif
            //$$ Item item = result.item().value();
            //#else
            ItemParser reader = new ItemParser(new StringReader(string), true);
            reader.parse();
            Item item = reader.getItem();
            //#endif

            //#if MC > 12004
            //$$ ItemStack stack = new ItemStack(item);
            //$$ stack.applyComponents(result.components());
            //$$ return stack;
            //#else
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
            //#endif
        } catch (CommandSyntaxException e) {
            SharedConstants.getLogger().error("Invalid item '{}'", string);
        }

        return ItemStack.EMPTY;
    }

    public void reParse() {
        if (this.shouldReParse) {
            this.setListContents(this.namesBlacklist, this.namesWhitelist);
        }
    }
}
