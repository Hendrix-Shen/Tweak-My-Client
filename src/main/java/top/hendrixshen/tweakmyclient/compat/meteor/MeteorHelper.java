package top.hendrixshen.tweakmyclient.compat.meteor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MeteorHelper {
    private static Method isNoSlowMethod;
    private static Object noSlowInstance;

    static {
        try {
            Class<?> meteorModules = Class.forName("meteordevelopment.meteorclient.systems.modules.Modules");
            Method getMeteorModulesMethod = meteorModules.getDeclaredMethod("get");
            getMeteorModulesMethod.setAccessible(true);
            Object meteorModulesInstance = getMeteorModulesMethod.invoke(null);
            Method getModuleMethod = meteorModules.getDeclaredMethod("get", Class.class);
            Class<?> noSlowClass = Class.forName("meteordevelopment.meteorclient.systems.modules.movement.NoSlow");
            getModuleMethod.setAccessible(true);
            MeteorHelper.noSlowInstance = getModuleMethod.invoke(meteorModulesInstance, noSlowClass);
            MeteorHelper.isNoSlowMethod = noSlowClass.getDeclaredMethod("items");
            MeteorHelper.isNoSlowMethod.setAccessible(true);
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static boolean isNoSlowdownHackEnable() {
        if (MeteorHelper.noSlowInstance != null) {
            try {
                return (boolean) MeteorHelper.isNoSlowMethod.invoke(MeteorHelper.noSlowInstance);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
