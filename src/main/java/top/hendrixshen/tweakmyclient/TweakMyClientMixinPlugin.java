package top.hendrixshen.tweakmyclient;

import top.hendrixshen.magiclib.api.dependencyValidator.mixin.MagicMixinPlugin;
import top.hendrixshen.magiclib.util.FabricUtil;

public class TweakMyClientMixinPlugin extends MagicMixinPlugin {
    @Override
    public void onLoad(String mixinPackage) {
        FabricUtil.customValidator(TweakMyClientReference.getModId());
    }
}
