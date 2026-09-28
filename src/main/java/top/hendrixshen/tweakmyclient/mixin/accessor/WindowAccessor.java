package top.hendrixshen.tweakmyclient.mixin.accessor;

import top.hendrixshen.magiclib.api.preprocess.DummyClass;

import org.spongepowered.asm.mixin.Mixin;

// CHECKSTYLE.OFF: JavadocStyle
/**
 * <li>mc1.14 ~ mc26.2: subproject 1.16.5 (main project) [dummy]        &lt;--------</li>
 * <li>mc26.3+        : subproject 26.3</li>
 */
// CHECKSTYLE.ON: JavadocStyle
@Mixin(DummyClass.class)
public interface WindowAccessor {
}
