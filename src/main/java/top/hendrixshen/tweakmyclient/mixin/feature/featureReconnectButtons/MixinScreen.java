package top.hendrixshen.tweakmyclient.mixin.feature.featureReconnectButtons;

//#if MC < 11700
//$$ import net.minecraft.client.gui.components.AbstractWidget;
//#endif
import net.minecraft.client.gui.components.Button;
//#if MC >= 11700
import net.minecraft.client.gui.components.events.GuiEventListener;
//#endif
import net.minecraft.client.gui.screens.DisconnectedScreen;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.tweakmyclient.TweakMyClient;
import top.hendrixshen.tweakmyclient.TweakMyClientPredicate;
import top.hendrixshen.tweakmyclient.config.Configs;
import top.hendrixshen.tweakmyclient.interfaces.IAutoReconnectScreen;
import top.hendrixshen.tweakmyclient.interfaces.IScreen;
import top.hendrixshen.tweakmyclient.util.AutoReconnectUtil;

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

    @Inject(
            method = "tick",
            at = @At(
                    value = "HEAD"
            )
    )
    private void onTick(CallbackInfo ci) {
        if (this instanceof IAutoReconnectScreen) {
            AutoReconnectUtil.tickAutoReconnectButton(((IAutoReconnectScreen) this).getParent());
        }
    }

    @Inject(
            method = "renderDirtBackground",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true)
    private void renderDirtBackground(int i, CallbackInfo ci) {
        if ((Object) this instanceof DisconnectedScreen) {
            if (Configs.expXiBao && TweakMyClientPredicate.xibaoLang.contains(TweakMyClient.getMinecraftClient().options.languageCode)) {
                AutoReconnectUtil.renderXibao(((Screen) (Object) this));
                ci.cancel();
            }
        }
    }
}
