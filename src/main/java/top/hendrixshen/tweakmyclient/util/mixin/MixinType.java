package top.hendrixshen.tweakmyclient.util.mixin;

public enum MixinType {
    // ModifyVariable
    MODIFY_ARG("modify"),
    MODIFY_ARGS("args"),
    MODIFY_CONSTANT("constant"),
    MODIFY_VARIABLE("localvar"),
    REDIRECT("redirect");

    private final String prefix;

    MixinType(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
}
