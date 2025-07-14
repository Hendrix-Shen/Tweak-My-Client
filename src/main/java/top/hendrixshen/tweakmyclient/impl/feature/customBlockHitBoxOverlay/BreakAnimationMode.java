package top.hendrixshen.tweakmyclient.impl.feature.customBlockHitBoxOverlay;

import lombok.AllArgsConstructor;
import lombok.Getter;
import top.hendrixshen.magiclib.api.malilib.config.option.EnumOptionEntry;
import top.hendrixshen.tweakmyclient.SharedConstants;

@Getter
@AllArgsConstructor
public enum BreakAnimationMode implements EnumOptionEntry {
    DOWN(true),
    NONE(false),
    SHRINK(true);

    public static final BreakAnimationMode DEFAULT = BreakAnimationMode.NONE;

    private final boolean forceDisableDepthTest;

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
        return SharedConstants.getModIdentifier().concat(".config.option.customBlockHitBoxBreakAnimation");
    }
}
