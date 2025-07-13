package top.hendrixshen.tweakmyclient.mixin.feature.autoReconnect;

import top.hendrixshen.magiclib.api.preprocess.DummyClass;

import org.spongepowered.asm.mixin.Mixin;

// CHECKSTYLE.OFF: JavadocStyle
/**
 * <li>mc1.14 ~ mc1.19.2: subproject 1.16.5 (main project) [dummy]        &lt;--------</li>
 * <li>mc1.19.3+        : subproject 1.21.3</li>
 */
// CHECKSTYLE.ON: JavadocStyle
@Mixin(DummyClass.class)
public abstract class MixinConnectScreen {
}
