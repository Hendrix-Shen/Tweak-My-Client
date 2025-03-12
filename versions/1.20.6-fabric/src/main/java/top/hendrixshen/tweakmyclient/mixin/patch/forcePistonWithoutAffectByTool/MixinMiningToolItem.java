package top.hendrixshen.tweakmyclient.mixin.patch.forcePistonWithoutAffectByTool;

import org.spongepowered.asm.mixin.Mixin;
import top.hendrixshen.magiclib.api.preprocess.DummyClass;

@Mixin(DummyClass.class)
public abstract class MixinMiningToolItem {
}
