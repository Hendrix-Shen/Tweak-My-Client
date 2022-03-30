package top.hendrixshen.tweakmyclient.compat.proxy.client;

import com.mojang.blaze3d.platform.Window;

public abstract class WindowCompatApi {
    protected static WindowCompatApi INSTANCE;

    public static WindowCompatApi getInstance() {
        return INSTANCE;
    }

    public abstract void resetTitle();

    public abstract Window getWindow();

    public abstract void setTitle(long window, String title);
}
