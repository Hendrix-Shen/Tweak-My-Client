package top.hendrixshen.tweakmyclient.mixin.disable.disableFovAffectedBySpeed;

import top.hendrixshen.magiclib.api.preprocess.DummyClass;

import org.spongepowered.asm.mixin.Mixin;

// CHECKSTYLE.OFF: JavadocStyle
/**
 * <li>mc1.14 ~ mc1.21.11: subproject 1.16.5 (main project)</li>
 * <li>mc26.1+           : subproject 26.1 [dummy]        &lt;--------</li>
 */
// CHECKSTYLE.ON: JavadocStyle
@Mixin(DummyClass.class)
public abstract class MixinGameRenderer {
}
