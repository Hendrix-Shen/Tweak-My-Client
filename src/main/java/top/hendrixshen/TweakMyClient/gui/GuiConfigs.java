package top.hendrixshen.TweakMyClient.gui;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.ConfigType;
import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.gui.GuiConfigsBase;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.button.IButtonActionListener;
import fi.dy.masa.malilib.util.StringUtils;
import top.hendrixshen.TweakMyClient.Reference;
import top.hendrixshen.TweakMyClient.config.Configs;

import java.util.Collections;
import java.util.List;

public class GuiConfigs extends GuiConfigsBase {
    private static ConfigGuiTab tab = ConfigGuiTab.GENERIC;

    public GuiConfigs() {
        super(10, 50, Reference.MOD_ID, null, StringUtils.translate(String.format("%s.gui.title.configs", Reference.MOD_ID)) + String.format(" (Version: %s)", Reference.MOD_VERSION));
    }

    @Override
    public void initGui() {
        super.initGui();
        this.clearOptions();

        int x = 10;
        int y = 26;

        for (ConfigGuiTab tab : ConfigGuiTab.values()) {
            x += this.createButton(x, y, -1, tab);
        }
    }

    private int createButton(int x, int y, int width, ConfigGuiTab tab) {
        ButtonGeneric button = new ButtonGeneric(x, y, width, 20, tab.getDisplayName());
        button.setEnabled(GuiConfigs.tab != tab);
        this.addButton(button, new GuiConfigs.ButtonListener(tab, this));

        return button.getWidth() + 2;
    }

    @Override
    protected int getConfigWidth() {
        ConfigGuiTab tab = GuiConfigs.tab;

        if (tab == ConfigGuiTab.GENERIC) {
            return 120;
        } else if (tab == ConfigGuiTab.FEATURE_TOGGLES) {
            return 80;
        } else if (tab == ConfigGuiTab.DISABLE_TOGGLES) {
            return 80;
        }

        return super.getConfigWidth();
    }

    @Override
    protected boolean useKeybindSearch() {
        return GuiConfigs.tab == ConfigGuiTab.GENERIC ||
                GuiConfigs.tab == ConfigGuiTab.DISABLE_HOTKEYS ||
                GuiConfigs.tab == ConfigGuiTab.FEATURE_HOTKEYS;
    }

    @Override
    public List<ConfigOptionWrapper> getConfigs() {
        List<? extends IConfigBase> configs;
        ConfigGuiTab tab = GuiConfigs.tab;

        if (tab == ConfigGuiTab.GENERIC) {
            configs = Configs.Generic.OPTIONS;
        } else if (tab == ConfigGuiTab.COLOR) {
            configs = Configs.Color.OPTIONS;
        } else if (tab == ConfigGuiTab.FEATURE_TOGGLES) {
            configs = ConfigUtils.createConfigWrapperForType(ConfigType.BOOLEAN, ImmutableList.copyOf(Configs.Feature.OPTIONS));
        } else if (tab == ConfigGuiTab.FEATURE_HOTKEYS) {
            configs = ConfigUtils.createConfigWrapperForType(ConfigType.HOTKEY, ImmutableList.copyOf(Configs.Feature.OPTIONS));
        } else if (tab == ConfigGuiTab.DISABLE_TOGGLES) {
            configs = ConfigUtils.createConfigWrapperForType(ConfigType.BOOLEAN, ImmutableList.copyOf(Configs.Disable.OPTIONS));
        } else if (tab == ConfigGuiTab.DISABLE_HOTKEYS) {
            configs = ConfigUtils.createConfigWrapperForType(ConfigType.HOTKEY, ImmutableList.copyOf(Configs.Disable.OPTIONS));
        } else {
            return Collections.emptyList();
        }

        return ConfigOptionWrapper.createFor(configs);
    }

    public enum ConfigGuiTab {
        GENERIC("generic"),
        COLOR("color"),
        FEATURE_TOGGLES("feature_toggle"),
        FEATURE_HOTKEYS("feature_hotkeys"),
        DISABLE_TOGGLES("disable_toggle"),
        DISABLE_HOTKEYS("disable_hotkeys");

        private final String name;

        ConfigGuiTab(String name) {
            this.name = name;
        }

        public String getDisplayName() {
            return StringUtils.translate(String.format("%s.gui.button.config_gui.%s", Reference.MOD_ID, name));
        }
    }

    private static class ButtonListener implements IButtonActionListener {
        private final GuiConfigs parent;
        private final ConfigGuiTab tab;

        public ButtonListener(ConfigGuiTab tab, GuiConfigs parent) {
            this.tab = tab;
            this.parent = parent;
        }

        @Override
        public void actionPerformedWithButton(ButtonBase button, int mouseButton) {
            GuiConfigs.tab = this.tab;
            this.parent.reCreateListWidget(); // apply the new config width
            this.parent.getListWidget().resetScrollbarPosition();
            this.parent.initGui();
        }
    }
}
