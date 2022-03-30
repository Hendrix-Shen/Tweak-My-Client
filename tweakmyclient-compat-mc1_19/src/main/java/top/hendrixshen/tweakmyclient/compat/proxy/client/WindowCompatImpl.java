package top.hendrixshen.tweakmyclient.compat.proxy.client;

import com.mojang.blaze3d.platform.Window;
import org.lwjgl.glfw.GLFW;
import top.hendrixshen.tweakmyclient.TweakMyClient;

public class WindowCompatImpl extends WindowCompatApi {
    public static void initCompat() {
        INSTANCE = new WindowCompatImpl();
    }

    @Override
    public void resetTitle() {
        TweakMyClient.getMinecraftClient().updateTitle();
    }

    @Override
    public Window getWindow() {
        return TweakMyClient.getMinecraftClient().getWindow();
    }

    @Override
    public void setTitle(long window, String title) {
        GLFW.glfwSetWindowTitle(window, title);
    }
}
