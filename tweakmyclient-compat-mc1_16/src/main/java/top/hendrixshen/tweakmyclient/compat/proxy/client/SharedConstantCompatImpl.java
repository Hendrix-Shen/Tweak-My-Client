package top.hendrixshen.tweakmyclient.compat.proxy.client;

import net.minecraft.SharedConstants;

public class SharedConstantCompatImpl extends SharedConstantCompatApi {
    public static void initCompat() {
        INSTANCE = new SharedConstantCompatImpl();
    }

    @Override
    public int getProtocolVersion() {
        return SharedConstants.getProtocolVersion();
    }

    @Override
    public String getCurrentVersionName() {
        return SharedConstants.getCurrentVersion().getName();
    }
}
