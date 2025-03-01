package top.hendrixshen.tweakmyclient.mixin.patch.chunkEdgeLagFix;

import org.spongepowered.asm.mixin.Mixin;
import top.hendrixshen.magiclib.api.preprocess.DummyClass;

@Mixin(DummyClass.class)
public abstract class MixinClientPacketListener {
}
