package top.hendrixshen.tweakmyclient.util;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.platform.Window;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import top.hendrixshen.tweakmyclient.TweakMyClient;
import top.hendrixshen.tweakmyclient.TweakMyClientReference;
import top.hendrixshen.tweakmyclient.compat.proxy.client.SharedConstantCompatApi;
import top.hendrixshen.tweakmyclient.compat.proxy.client.WindowCompatApi;
import top.hendrixshen.tweakmyclient.config.Configs;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomWindowUtil {
    public static final HashMap<String, String> PLACEHOLDER_MAP = Maps.newHashMap();
    public static final HashMap<String, String> PLACEHOLDER_STATIC_MAP = Maps.newHashMap();
    public static final Pattern MOD_PATTERN = Pattern.compile("(?<=(\\{fabric_mod_ver:)).*?(?=(}))");
    private static String TITLE_CACHE;
    private static String TITLE_CACHE_WITH_ACTIVITY;

    public static final Minecraft mc = TweakMyClient.getMinecraftClient();

    // These data should not be changed
    static {
        PLACEHOLDER_STATIC_MAP.put("{fabric_loader_version}", FabricLoaderImpl.VERSION);
        PLACEHOLDER_STATIC_MAP.put("{fabric_loader_asm_version}", String.valueOf(FabricLoaderImpl.ASM_VERSION));
        PLACEHOLDER_STATIC_MAP.put("{mc_protocol_version}", Integer.toString(SharedConstantCompatApi.getInstance().getProtocolVersion()));
        PLACEHOLDER_STATIC_MAP.put("{mc_version}", SharedConstantCompatApi.getInstance().getCurrentVersionName());
        PLACEHOLDER_STATIC_MAP.put("{tmc_version}", TweakMyClientReference.getModVersion());
        PLACEHOLDER_STATIC_MAP.put("{tmc_version_type}", TweakMyClientReference.getModVersionType());
        if (Configs.featureCustomWindowTitle) {
            rebuildCache(TitleType.TITLE);
            rebuildCache(TitleType.TITLE_WITH_ACTIVITY);
        }
    }

    public static String replacePlaceholders(HashMap<String, String> map, String str) {
        for (String key : map.keySet()) {
            str = str.replace(key, map.getOrDefault(key, "Null"));
        }
        return str;
    }

    public static String getWindowTitle() {
        return replacePlaceholders(PLACEHOLDER_MAP, (hasActivity() ? TITLE_CACHE_WITH_ACTIVITY : TITLE_CACHE));
    }

    public static String getActivity() {
        if (mc.getSingleplayerServer() != null && !mc.getSingleplayerServer().isPublished()) {
            return I18n.get("title.singleplayer");
        } else if (mc.isConnectedToRealms()) {
            return I18n.get("title.multiplayer.realms");
        } else if (mc.getSingleplayerServer() == null && (mc.getCurrentServer() == null || !mc.getCurrentServer().isLan())) {
            return I18n.get("title.multiplayer.other");
        } else {
            return I18n.get("title.multiplayer.lan");
        }
    }

    public static boolean hasActivity() {
        ClientPacketListener clientPacketListener = mc.getConnection();
        return clientPacketListener != null && clientPacketListener.getConnection().isConnected();
    }

    public static void updatePlaceholders() {
        // Maybe changed by other mods.
        PLACEHOLDER_MAP.put("{mc_username}", TweakMyClient.getMinecraftClient().getUser().getName());
        // Activity data.
        PLACEHOLDER_MAP.put("{mc_activity}", hasActivity() ? getActivity() : "Null");
    }

    public static void updateFPS(int fps) {
        PLACEHOLDER_MAP.put("{mc_fps}", String.valueOf(fps));
    }

    public static void rebuildCache(TitleType type) {
        if (type == TitleType.TITLE) {
            TITLE_CACHE = replacePlaceholders(PLACEHOLDER_STATIC_MAP, Configs.customWindowTitle);
            TITLE_CACHE = replaceModVersion(TITLE_CACHE);
        } else if (type == TitleType.TITLE_WITH_ACTIVITY) {
            TITLE_CACHE_WITH_ACTIVITY = replacePlaceholders(PLACEHOLDER_STATIC_MAP, Configs.customWindowTitleWithActivity);
            TITLE_CACHE_WITH_ACTIVITY = replaceModVersion(TITLE_CACHE_WITH_ACTIVITY);
        }
    }

    public static String replaceModVersion(String str) {
        Matcher matcher = MOD_PATTERN.matcher(str);
        while (matcher.find()) {
            String group = matcher.group();
            Optional<ModContainer> container = FabricLoader.getInstance().getModContainer(group);
            str = str.replace(String.format("{fabric_mod_ver:%s}", group), container.isPresent() ? container.get().getMetadata().getVersion().getFriendlyString() : "Null");
        }
        return str;
    }

    public enum TitleType {
        TITLE,
        TITLE_WITH_ACTIVITY
    }

    public static void reSetTitle() {
        WindowCompatApi.getInstance().resetTitle();
    }

    public static void updateTitle() {
        updatePlaceholders();
        WindowCompatApi.getInstance().setTitle(WindowCompatApi.getInstance().getWindow().getWindow(), getWindowTitle());
    }

    public static void updateIcon(Window window) {
        try {
            InputStream icon16x;
            InputStream icon32x;
            if (Configs.featureCustomWindowIcon) {
                icon16x = mc.getResourceManager().getResource(new ResourceLocation(TweakMyClientReference.getModId(), "icons/icon_16x16.png")).getInputStream();
                icon32x = mc.getResourceManager().getResource(new ResourceLocation(TweakMyClientReference.getModId(), "icons/icon_32x32.png")).getInputStream();
            } else {
                icon16x = mc.getClientPackSource().getVanillaPack().getResource(PackType.CLIENT_RESOURCES, new ResourceLocation("icons/icon_16x16.png"));
                icon32x = mc.getClientPackSource().getVanillaPack().getResource(PackType.CLIENT_RESOURCES, new ResourceLocation("icons/icon_32x32.png"));
            }
            window.setIcon(icon16x, icon32x);
        } catch (IOException e) {
            TweakMyClient.getLogger().error("Couldn't set icon", e);
        }
    }
}
