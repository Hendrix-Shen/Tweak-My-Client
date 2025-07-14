package top.hendrixshen.tweakmyclient.impl.patch.endPortalRendererFix;

import top.hendrixshen.magiclib.api.malilib.config.option.EnumOptionEntry;
import top.hendrixshen.tweakmyclient.SharedConstants;

public enum EndPortalRenderMode implements EnumOptionEntry {
    ACTUAL,
    FULL,
    LEGACY,
    MODERN;

    public static final EndPortalRenderMode DEFAULT = EndPortalRenderMode.LEGACY;

    @Override
    public EnumOptionEntry[] getAllValues() {
        return EndPortalRenderMode.values();
    }

    @Override
    public EnumOptionEntry getDefault() {
        return EndPortalRenderMode.DEFAULT;
    }

    @Override
    public String getTranslationPrefix() {
        return SharedConstants.getModIdentifier().concat(".config.option.endPortalRenderMode");
    }
}
