package top.hendrixshen.tweakmyclient.mixin.feature.daylightOverride;

import top.hendrixshen.magiclib.api.preprocess.DummyClass;

import org.spongepowered.asm.mixin.Mixin;

// CHECKSTYLE.OFF: JavadocStyle
/**
 * <li>mc1.14 ~ mc1.15: subproject 1.15.2 [dummy]        &lt;--------</li>
 * <li>mc1.16+        : subproject 1.16.5 (main project)</li>
 */
// CHECKSTYLE.ON: JavadocStyle
@Mixin(DummyClass.class)
public abstract class MixinClientLevelClientLevelData {
}
