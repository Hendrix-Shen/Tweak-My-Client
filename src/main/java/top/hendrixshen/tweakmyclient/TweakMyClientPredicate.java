package top.hendrixshen.tweakmyclient;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import top.hendrixshen.magiclib.config.Option;
import top.hendrixshen.magiclib.dependency.annotation.OptionDependencyPredicate;
import top.hendrixshen.tweakmyclient.config.Configs;

import java.util.List;

public class TweakMyClientPredicate {
    public static final List<String> xibaoLang = ImmutableList.of("lzh ", "zh_cn", "zh_hk", "zh_tw");

    public static class AllowBreakAnimation implements OptionDependencyPredicate {
        @Override
        public boolean test(Option option) {
            return Configs.featureCustomBlockHitBoxOverlayFill && Configs.featureCustomBlockHitBoxOverlayOutline;
        }
    }

    public static class CustomWindowTitleEnableActivity implements OptionDependencyPredicate {
        @Override
        public boolean test(Option option) {
            return Configs.customWindowTitleEnableActivity;
        }
    }

    public static class DebugMode implements OptionDependencyPredicate {
        @Override
        public boolean test(Option option) {
            return Configs.debugMode;
        }
    }

    public static class ExperimentalMode implements OptionDependencyPredicate {
        @Override
        public boolean test(Option option) {
            return Configs.debugExperimentalMode && Configs.debugMode;
        }
    }

    public static class ExpXibao implements OptionDependencyPredicate {
        @Override
        public boolean test(Option option) {
            return Configs.debugExperimentalMode && Configs.debugMode &&
                    TweakMyClientPredicate.xibaoLang.contains(TweakMyClient.getMinecraftClient().options.languageCode);
        }
    }
}
