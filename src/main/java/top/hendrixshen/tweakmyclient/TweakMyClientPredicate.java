package top.hendrixshen.tweakmyclient;

import com.google.common.collect.ImmutableList;
import top.hendrixshen.magiclib.dependency.api.ConfigDependencyPredicate;
import top.hendrixshen.magiclib.malilib.impl.ConfigOption;
import top.hendrixshen.tweakmyclient.config.Configs;

import java.util.List;

public class TweakMyClientPredicate {
    public static final List<String> xibaoLang = ImmutableList.of("lzh ", "zh_cn", "zh_hk", "zh_tw");

    public static class AllowBreakAnimation implements ConfigDependencyPredicate {
        @Override
        public boolean isSatisfied(ConfigOption option) {
            return Configs.featureCustomBlockHitBoxOverlayFill && Configs.featureCustomBlockHitBoxOverlayOutline;
        }
    }

    public static class CustomWindowTitleEnableActivity implements ConfigDependencyPredicate {
        @Override
        public boolean isSatisfied(ConfigOption option) {
            return Configs.customWindowTitleEnableActivity;
        }
    }

    public static class DebugMode implements ConfigDependencyPredicate {
        @Override
        public boolean isSatisfied(ConfigOption option) {
            return Configs.debugMode;
        }
    }

    public static class ExperimentalMode implements ConfigDependencyPredicate {
        @Override
        public boolean isSatisfied(ConfigOption option) {
            return Configs.debugExperimentalMode && Configs.debugMode;
        }
    }

    public static class ExpXiBao implements ConfigDependencyPredicate {
        @Override
        public boolean isSatisfied(ConfigOption option) {
            return Configs.debugExperimentalMode && Configs.debugMode &&
                    TweakMyClientPredicate.xibaoLang.contains(TweakMyClient.getMinecraftClient().options.languageCode);
        }
    }
}
