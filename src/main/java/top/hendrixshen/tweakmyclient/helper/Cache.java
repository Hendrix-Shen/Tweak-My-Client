package top.hendrixshen.tweakmyclient.helper;

import fi.dy.masa.malilib.util.Color4f;
import lombok.Getter;
import net.minecraft.world.item.Item;
import top.hendrixshen.tweakmyclient.config.Configs;
import top.hendrixshen.tweakmyclient.util.StringUtil;

import java.util.HashSet;

public class Cache {
    @Getter(lazy = true)
    private static final Cache instance = new Cache();
    private final HashSet<Item> autoDropBlackList = new HashSet<>();
    private final HashSet<Item> autoDropWhiteList = new HashSet<>();
    private Color4f breakingRestrictionBoxBlacklistModeOutlineColor;
    private Color4f breakingRestrictionBoxWhitelistModeOutlineColor;
    private final HashSet<AreaBox> breakingRestrictionBoxBlacklist = new HashSet<>();
    private final HashSet<AreaBox> breakingRestrictionBoxWhiteList = new HashSet<>();
    private final HashSet<Item> itemGlowingBlackList = new HashSet<>();

    public void cacheAutoDropBlackList() {
        this.autoDropBlackList.clear();
        this.autoDropBlackList.addAll(StringUtil.getItemStackSets(Configs.listAutoDropBlackList));
    }

    public void cacheAutoDropWhiteList() {
        this.autoDropWhiteList.clear();
        this.autoDropWhiteList.addAll(StringUtil.getItemStackSets(Configs.listAutoDropWhiteList));
    }

    public void cacheBreakingRestrictionBoxBlacklistModeOutlineColor() {
        this.breakingRestrictionBoxBlacklistModeOutlineColor = Color4f.fromColor(Configs.colorBreakingRestrictionBoxBlacklistMode, 1.0F);
    }

    public void cacheBreakingRestrictionBoxWhitelistModeOutlineColor() {
        this.breakingRestrictionBoxWhitelistModeOutlineColor = Color4f.fromColor(Configs.colorBreakingRestrictionBoxWhitelistMode, 1.0F);
    }

    public void cacheBreakingRestrictionBoxBlacklist() {
        this.breakingRestrictionBoxBlacklist.clear();
        this.breakingRestrictionBoxBlacklist.addAll(StringUtil.getAreaBoxSets(Configs.listBreakingRestrictionBoxBlacklist));
    }

    public void cacheBreakingRestrictionBoxWhiteList() {
        this.breakingRestrictionBoxWhiteList.clear();
        this.breakingRestrictionBoxWhiteList.addAll(StringUtil.getAreaBoxSets(Configs.listBreakingRestrictionBoxWhitelist));
    }

    public void cacheItemGlowingBlackList() {
        this.itemGlowingBlackList.clear();
        this.itemGlowingBlackList.addAll(StringUtil.getItemStackSets(Configs.listItemGlowingBlacklist));
    }

    public HashSet<Item> getAutoDropBlackList() {
        return autoDropBlackList;
    }

    public HashSet<Item> getAutoDropWhiteList() {
        return autoDropWhiteList;
    }

    public Color4f getBreakingRestrictionBoxBlacklistModeOutlineColor() {
        return breakingRestrictionBoxBlacklistModeOutlineColor;
    }

    public Color4f getBreakingRestrictionBoxWhitelistModeOutlineColor() {
        return breakingRestrictionBoxWhitelistModeOutlineColor;
    }

    public HashSet<AreaBox> getBreakingRestrictionBoxBlacklist() {
        return breakingRestrictionBoxBlacklist;
    }

    public HashSet<AreaBox> getBreakingRestrictionBoxWhiteList() {
        return breakingRestrictionBoxWhiteList;
    }

    public HashSet<Item> getItemGlowingBlackList() {
        return itemGlowingBlackList;
    }

    public void rebuildAllCache() {
        this.cacheAutoDropBlackList();
        this.cacheAutoDropWhiteList();
        this.cacheBreakingRestrictionBoxBlacklistModeOutlineColor();
        this.cacheBreakingRestrictionBoxWhitelistModeOutlineColor();
        this.cacheBreakingRestrictionBoxBlacklist();
        this.cacheBreakingRestrictionBoxWhiteList();
        this.cacheItemGlowingBlackList();
    }
}
