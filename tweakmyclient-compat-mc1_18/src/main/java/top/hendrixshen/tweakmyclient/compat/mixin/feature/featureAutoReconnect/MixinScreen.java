package top.hendrixshen.tweakmyclient.compat.mixin.feature.featureAutoReconnect;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import top.hendrixshen.tweakmyclient.fakeInterface.IScreen;

@Mixin(Screen.class)
public abstract class MixinScreen implements IScreen {
    @SuppressWarnings("target")
    @Shadow
    protected abstract GuiEventListener addRenderableWidget(GuiEventListener guiEventListener);

    @Override
    public Button addButtonEx(Button button) {
        return (Button) addRenderableWidget(button);
    }
}
