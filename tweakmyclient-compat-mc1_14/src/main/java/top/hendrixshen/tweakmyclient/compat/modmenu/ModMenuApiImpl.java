package top.hendrixshen.tweakmyclient.compat.modmenu;

import io.github.prospector.modmenu.api.ModMenuApi;
import net.minecraft.client.gui.screens.Screen;
import top.hendrixshen.tweakmyclient.TweakMyClientConfigGui;
import top.hendrixshen.tweakmyclient.TweakMyClientReference;

import java.util.function.Function;

public class ModMenuApiImpl implements ModMenuApi {
    @Override
    public String getModId() {
        return TweakMyClientReference.getModId();
    }

    @Override
    public Function<Screen, ? extends Screen> getConfigScreenFactory() {
        return (screen) -> {
            TweakMyClientConfigGui gui = TweakMyClientConfigGui.getInstance();
            gui.setParent(screen);
            return gui;
        };
    }
}
