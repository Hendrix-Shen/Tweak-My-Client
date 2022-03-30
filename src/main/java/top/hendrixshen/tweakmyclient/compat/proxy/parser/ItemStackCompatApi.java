package top.hendrixshen.tweakmyclient.compat.proxy.parser;

import net.minecraft.world.item.ItemStack;

public abstract class ItemStackCompatApi {
    protected static ItemStackCompatApi INSTANCE;

    public static ItemStackCompatApi getInstance() {
        return INSTANCE;
    }

    public abstract ItemStack toItemStack(String string);
}
