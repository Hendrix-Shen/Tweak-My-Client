package top.hendrixshen.tweakmyclient.mixin.patch.chunkEdgeLagFix;

import top.hendrixshen.magiclib.api.preprocess.DummyClass;

import org.spongepowered.asm.mixin.Mixin;

// CHECKSTYLE.OFF: JavadocStyle
/**
 * <li>mc1.14 ~ mc1.19: subproject 1.16.5 (main project)</li>
 * <li>mc1.20+        : subproject 1.20.1 [dummy]        &lt;--------</li>
 */
// CHECKSTYLE.ON: JavadocStyle
@Mixin(DummyClass.class)
public abstract class MixinClientPacketListener {
}
