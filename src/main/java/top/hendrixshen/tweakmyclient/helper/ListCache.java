package top.hendrixshen.tweakmyclient.helper;

import net.minecraft.world.item.Item;

import java.util.HashSet;

public class ListCache {
    public static HashSet<Item> itemGlowingBlacklist = new HashSet<>();
    public static HashSet<Item> itemAutoDropBlackList = new HashSet<>();
    public static HashSet<Item> itemAutoDropWhiteList = new HashSet<>();
}
