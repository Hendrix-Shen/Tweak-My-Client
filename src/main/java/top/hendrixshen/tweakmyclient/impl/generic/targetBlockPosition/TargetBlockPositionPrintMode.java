package top.hendrixshen.tweakmyclient.impl.generic.targetBlockPosition;

import top.hendrixshen.magiclib.api.malilib.config.option.EnumOptionEntry;
import top.hendrixshen.tweakmyclient.SharedConstants;

public enum TargetBlockPositionPrintMode implements EnumOptionEntry {
    PUBLIC,
    PRIVATE
    ;

    public static final TargetBlockPositionPrintMode DEFAULT = TargetBlockPositionPrintMode.PRIVATE;

    @Override
    public EnumOptionEntry[] getAllValues() {
        return TargetBlockPositionPrintMode.values();
    }

    @Override
    public EnumOptionEntry getDefault() {
        return TargetBlockPositionPrintMode.DEFAULT;
    }

    @Override
    public String getTranslationPrefix() {
        return SharedConstants.getModIdentifier().concat("label.targetBlockPositionPrintMode");
    }
}
