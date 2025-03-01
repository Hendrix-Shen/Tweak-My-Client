package top.hendrixshen.tweakmyclient.mixin.disable.disableRenderOverlayFire;

import org.spongepowered.asm.mixin.Mixin;
import top.hendrixshen.magiclib.api.preprocess.DummyClass;

@Mixin(DummyClass.class)
public abstract class MixinItemInHandRenderer {
}
