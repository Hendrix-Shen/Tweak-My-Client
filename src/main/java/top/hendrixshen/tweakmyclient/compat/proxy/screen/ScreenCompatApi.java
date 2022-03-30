package top.hendrixshen.tweakmyclient.compat.proxy.screen;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;

public abstract class ScreenCompatApi {
    protected static ScreenCompatApi INSTANCE;

    public static ScreenCompatApi getInstance() {
        return INSTANCE;
    }

    public abstract Button addButton(Screen screen, Button button);

    public abstract Button createButton(int x, int y, int width, int height, String message, Button.OnPress onPress);

    public abstract void setButtonMessage(Button button, String message);
}
