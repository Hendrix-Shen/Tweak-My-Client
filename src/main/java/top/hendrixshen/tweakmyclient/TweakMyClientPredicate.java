package top.hendrixshen.tweakmyclient;

import top.hendrixshen.magiclib.config.Option;
import top.hendrixshen.magiclib.dependency.annotation.OptionDependencyPredicate;
import top.hendrixshen.tweakmyclient.config.Configs;

public class TweakMyClientPredicate {
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
            return Configs.debugExperimentalMode;
        }
    }
}
