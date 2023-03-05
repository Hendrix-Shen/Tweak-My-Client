package top.hendrixshen.tweakmyclient.mixin.feature.featureReconnectButtons;

//#if MC < 11700
//$$ import net.minecraft.client.gui.components.AbstractWidget;
//#endif
import net.minecraft.client.gui.components.Button;
//#if MC >= 11700
import net.minecraft.client.gui.components.events.GuiEventListener;
//#endif
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import top.hendrixshen.tweakmyclient.interfaces.IScreen;

@Mixin(Screen.class)
public abstract class MixinScreen implements IScreen {
    @Shadow
    //#if MC >= 11700
    @SuppressWarnings("target")
    protected abstract GuiEventListener addRenderableWidget(GuiEventListener guiEventListener);
    //#else
    //$$ protected abstract AbstractWidget addButton(AbstractWidget abstractWidget);
    //#endif

    @Override
    public Button tmc$addButton(Button button) {
        //#if MC >= 11700
        return (Button) this.addRenderableWidget(button);
        //#else
        //$$ return (Button) this.addButton(button);
        //#endif
    }
}
