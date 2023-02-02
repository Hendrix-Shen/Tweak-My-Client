package top.hendrixshen.tweakmyclient.compat.modmenu;

import top.hendrixshen.tweakmyclient.TweakMyClientReference;

public class WrapperModMenuApiImpl extends ModMenuApiImpl {
    @Override
    public String getModIdCompat() {
        return TweakMyClientReference.getModIdentifier();
    }
}