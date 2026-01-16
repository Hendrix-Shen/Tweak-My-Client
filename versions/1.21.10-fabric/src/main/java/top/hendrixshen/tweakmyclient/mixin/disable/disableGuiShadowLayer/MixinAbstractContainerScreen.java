package top.hendrixshen.tweakmyclient.mixin.disable.disableGuiShadowLayer;

import top.hendrixshen.magiclib.api.preprocess.DummyClass;

import net.minecraft.world.inventory.AbstractContainerMenu;

import org.spongepowered.asm.mixin.Mixin;

// CHECKSTYLE.OFF: JavadocStyle
/**
 * <li>mc1.14 ~ mc1.20.4: subproject 1.16.5 (main project) [dummy]</li>
 * <li>mc1.20.5+        : subproject 1.20.6</li>
 * <li>mc1.21.10+       : subproject 1.20.10 [dummy]        &lt;--------</li>
 */
// CHECKSTYLE.ON: JavadocStyle
@Mixin(DummyClass.class)
public abstract class MixinAbstractContainerScreen<T extends AbstractContainerMenu> {
}
