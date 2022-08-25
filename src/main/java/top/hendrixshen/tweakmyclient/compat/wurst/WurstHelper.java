package top.hendrixshen.tweakmyclient.compat.wurst;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class WurstHelper {
    private static Method isNoSlowdownHackInstance;
    private static Object noSlowdownHackInstance;

    static {
        try {
            Class<?> wurstClient = Class.forName("net.wurstclient.WurstClient");
            Object wurstClientInstance = wurstClient.getDeclaredField("INSTANCE").get(null);
            Field haxField = wurstClient.getDeclaredField("hax");
            haxField.setAccessible(true);
            Object hackListInstance = haxField.get(wurstClientInstance);
            Class<?> hackListClass = Class.forName("net.wurstclient.hack.HackList");
            Field noSlowdownHackField = hackListClass.getDeclaredField("noSlowdownHack");
            noSlowdownHackField.setAccessible(true);
            WurstHelper.noSlowdownHackInstance = noSlowdownHackField.get(hackListInstance);
            Class<?> noSlowdownHackClass = Class.forName("net.wurstclient.hacks.NoSlowdownHack");
            WurstHelper.isNoSlowdownHackInstance = noSlowdownHackClass.getMethod("isEnabled");
            WurstHelper.isNoSlowdownHackInstance.setAccessible(true);
        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public static boolean isNoSlowdownHackEnable() {
        if (WurstHelper.isNoSlowdownHackInstance != null) {
            try {
                return (boolean) WurstHelper.isNoSlowdownHackInstance.invoke(WurstHelper.noSlowdownHackInstance);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
