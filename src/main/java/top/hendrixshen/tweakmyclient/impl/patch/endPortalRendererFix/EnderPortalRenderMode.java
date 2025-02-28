package top.hendrixshen.tweakmyclient.impl.patch.endPortalRendererFix;

import top.hendrixshen.magiclib.api.malilib.config.option.EnumOptionEntry;
import top.hendrixshen.tweakmyclient.SharedConstants;

public enum EnderPortalRenderMode implements EnumOptionEntry {
    ACTUAL,
    FULL,
    LEGACY,
    MODERN
    ;

    public static final EnderPortalRenderMode DEFAULT = EnderPortalRenderMode.LEGACY;

    @Override
    public EnumOptionEntry[] getAllValues() {
        return EnderPortalRenderMode.values();
    }

    @Override
    public EnumOptionEntry getDefault() {
        return EnderPortalRenderMode.DEFAULT;
    }

    @Override
    public String getTranslationPrefix() {
        return SharedConstants.getModIdentifier().concat("label.enderPortalRenderMode");
    }
}