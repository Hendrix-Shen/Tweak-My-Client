package top.hendrixshen.tweakmyclient.mixin.patch.endPortalRendererFix;

import top.hendrixshen.magiclib.api.preprocess.DummyClass;

import org.spongepowered.asm.mixin.Mixin;

// CHECKSTYLE.OFF: JavadocStyle
/**
 * <li>mc1.14            : subproject 1.14.4</li>
 * <li>mc1.15 ~ mc1.21.11: subproject 1.16.5 (main project)</li>
 * <li>mc26.1+           : subproject 26.1.2 [dummy]        &lt;--------</li>
 */
// CHECKSTYLE.ON: JavadocStyle
@Mixin(DummyClass.class)
public abstract class MixinTheEndPortalRenderer {
}
