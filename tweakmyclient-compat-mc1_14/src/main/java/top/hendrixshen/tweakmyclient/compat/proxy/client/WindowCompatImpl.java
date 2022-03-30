package top.hendrixshen.tweakmyclient.compat.proxy.client;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.SharedConstants;
import org.lwjgl.glfw.GLFW;
import top.hendrixshen.tweakmyclient.TweakMyClient;

public class WindowCompatImpl extends WindowCompatApi {
    public static void initCompat() {
        INSTANCE = new WindowCompatImpl();
    }

    @Override
    public void resetTitle() {
        GLFW.glfwSetWindowTitle(this.getWindow().getWindow(), "Minecraft " + SharedConstants.getCurrentVersion().getName());
    }

    @Override
    public Window getWindow() {
        return TweakMyClient.getMinecraftClient().window;
    }

    @Override
    public void setTitle(long window, String title) {
        GLFW.glfwSetWindowTitle(window, title);
    }
}
