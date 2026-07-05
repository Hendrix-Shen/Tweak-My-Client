package top.hendrixshen.tweakmyclient.mixin.disable.disableRenderScoreboard;

import top.hendrixshen.magiclib.api.preprocess.DummyClass;

import org.spongepowered.asm.mixin.Mixin;

// CHECKSTYLE.OFF: JavadocStyle
/**
 * <li>mc1.14 ~ mc26.1: subproject 1.16.5 (main project)</li>
 * <li>mc26.2+        : subproject 26.2 [dummy]        &lt;--------</li>
 */
// CHECKSTYLE.ON: JavadocStyle
@Mixin(DummyClass.class)
public abstract class MixinGui {
}
