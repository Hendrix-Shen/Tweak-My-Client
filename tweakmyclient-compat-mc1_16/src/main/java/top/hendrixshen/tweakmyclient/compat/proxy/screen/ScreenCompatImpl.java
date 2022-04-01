package top.hendrixshen.tweakmyclient.compat.proxy.screen;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import top.hendrixshen.tweakmyclient.fakeInterface.IScreen;

public class ScreenCompatImpl extends ScreenCompatApi {
    public static void initCompat() {
        INSTANCE = new ScreenCompatImpl();
    }

    @Override
    public Button addButton(Screen screen, Button button) {
        return ((IScreen) screen).addButtonEx(button);
    }

    @Override
    public Button createButton(int x, int y, int width, int height, String message, Button.OnPress onPress) {
        return new Button(x, y, width, height, new TextComponent(message), onPress);
    }

    @Override
    public void setButtonMessage(Button button, String message) {
        button.setMessage(new TextComponent(message));
    }
}
