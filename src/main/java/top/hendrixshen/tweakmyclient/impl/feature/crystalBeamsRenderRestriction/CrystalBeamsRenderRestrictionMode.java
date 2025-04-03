package top.hendrixshen.tweakmyclient.impl.feature.crystalBeamsRenderRestriction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import top.hendrixshen.magiclib.api.malilib.config.option.EnumOptionEntry;
import top.hendrixshen.tweakmyclient.SharedConstants;

@Getter
@AllArgsConstructor
public enum CrystalBeamsRenderRestrictionMode implements EnumOptionEntry {
    FIXED_ONLY(true, false),
    NONE(true, true),
    TRACKING_ONLY(false, true)
    ;

    public static final CrystalBeamsRenderRestrictionMode DEFAULT = CrystalBeamsRenderRestrictionMode.NONE;

    private final boolean crystalBeamAllow;
    private final boolean enderDragonBeamAllow;

    @Override
    public EnumOptionEntry[] getAllValues() {
        return CrystalBeamsRenderRestrictionMode.values();
    }

    @Override
    public EnumOptionEntry getDefault() {
        return CrystalBeamsRenderRestrictionMode.DEFAULT;
    }

    @Override
    public String getTranslationPrefix() {
        return SharedConstants.getModIdentifier().concat(".config.option.crystalBeamsRenderRestrictionType");
    }
}
