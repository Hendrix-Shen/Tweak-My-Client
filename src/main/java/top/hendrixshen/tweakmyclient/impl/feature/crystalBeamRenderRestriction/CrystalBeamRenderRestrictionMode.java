package top.hendrixshen.tweakmyclient.impl.feature.crystalBeamRenderRestriction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import top.hendrixshen.magiclib.api.malilib.config.option.EnumOptionEntry;
import top.hendrixshen.tweakmyclient.SharedConstants;

@Getter
@AllArgsConstructor
public enum CrystalBeamRenderRestrictionMode implements EnumOptionEntry {
    BLOCK(false, false),
    FIXED_ONLY(true, false),
    NONE(true, true),
    TRACKING_ONLY(false, true)
    ;

    public static final CrystalBeamRenderRestrictionMode DEFAULT = CrystalBeamRenderRestrictionMode.NONE;

    private final boolean crystalBeamAllow;
    private final boolean enderDragonBeamAllow;

    @Override
    public EnumOptionEntry[] getAllValues() {
        return CrystalBeamRenderRestrictionMode.values();
    }

    @Override
    public EnumOptionEntry getDefault() {
        return CrystalBeamRenderRestrictionMode.DEFAULT;
    }

    @Override
    public String getTranslationPrefix() {
        return SharedConstants.getModIdentifier().concat(".config.option.crystalBeamRenderRestrictionType");
    }
}
