package top.hendrixshen.tweakmyclient.mixin.patch.forcePistonWithoutAffectByTool;

import top.hendrixshen.magiclib.api.preprocess.DummyClass;

import org.spongepowered.asm.mixin.Mixin;

// CHECKSTYLE.OFF: JavadocStyle
/**
 * <li>mc1.14 ~ mc1.20.4: subproject 1.16.5 (main project) [dummy]        &lt;--------</li>
 * <li>mc1.20.5+        : subproject 1.20.6</li>
 */
// CHECKSTYLE.ON: JavadocStyle
@Mixin(DummyClass.class)
public abstract class MixinItem {
}
