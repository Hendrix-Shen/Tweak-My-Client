package top.hendrixshen.TweakMyClient;

import top.hendrixshen.magiclib.untils.fabricloader.DependencyValidator;
import top.hendrixshen.magiclib.untils.mixin.MagicMixinPlugin;

public class TweakMyClientMixinPlugin extends MagicMixinPlugin {
    @Override
    public void onLoad(String mixinPackage) {
        DependencyValidator.customValidator(TweakMyClientReference.getModId(), "compatible");
    }
}
