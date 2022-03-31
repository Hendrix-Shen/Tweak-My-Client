package top.hendrixshen.tweakmyclient.compat.proxy.client;

public abstract class SharedConstantCompatApi {
    protected static SharedConstantCompatApi INSTANCE;

    public static SharedConstantCompatApi getInstance() {
        return INSTANCE;
    }

    public abstract int getProtocolVersion();

    public abstract String getCurrentVersionName();
}
