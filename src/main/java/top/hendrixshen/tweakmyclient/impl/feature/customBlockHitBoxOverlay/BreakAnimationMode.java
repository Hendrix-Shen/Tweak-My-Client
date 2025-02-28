package top.hendrixshen.tweakmyclient.impl.feature.customBlockHitBoxOverlay;

import top.hendrixshen.magiclib.api.malilib.config.option.EnumOptionEntry;
import top.hendrixshen.tweakmyclient.SharedConstants;

public enum BreakAnimationMode implements EnumOptionEntry {
    DOWN,
    NONE,
    SHRINK
    ;

    public static final BreakAnimationMode DEFAULT = BreakAnimationMode.NONE;

    @Override
    public EnumOptionEntry[] getAllValues() {
        return BreakAnimationMode.values();
    }

    @Override
    public EnumOptionEntry getDefault() {
        return BreakAnimationMode.DEFAULT;
    }

    @Override
    public String getTranslationPrefix() {
        return SharedConstants.getModIdentifier().concat("label.breakAnimationMode");
    }
}
