package top.hendrixshen.TweakMyClient;

import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class TweakMyClientMixinPlugin implements IMixinConfigPlugin {
    private static final String MIXIN_AUTHME = ".authme.";
    private static final String MIXIN_REAUTH = ".reauth.";
    public static boolean isAuthMeLoaded;
    public static boolean isReAuthLoaded;
    public final String AUTHME_ID = "authme";
    private final String REAUTH_ID = "reauth";

    @Override
    public void onLoad(String mixinPackage) {
        isAuthMeLoaded = FabricLoader.getInstance().isModLoaded(this.AUTHME_ID);
        isReAuthLoaded = FabricLoader.getInstance().isModLoaded(this.REAUTH_ID);
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (!isAuthMeLoaded && mixinClassName.contains(MIXIN_AUTHME)) {
            return false;
        } else return isReAuthLoaded || !mixinClassName.contains(MIXIN_REAUTH);
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}
