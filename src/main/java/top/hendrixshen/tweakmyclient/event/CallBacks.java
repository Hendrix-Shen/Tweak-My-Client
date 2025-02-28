package top.hendrixshen.tweakmyclient.event;

// import sun.misc.Unsafe;
// import top.hendrixshen.tweakmyclient.SharedConstants;
//
// import java.lang.reflect.Field;

public class CallBacks {
    // private static Unsafe UNSAFE = null;
    //
    // static {
    //     try {
    //         Class<?> unsafe = Class.forName("sun.misc.Unsafe");
    //         Field field = unsafe.getDeclaredField("theUnsafe");
    //         field.setAccessible(true);
    //         CallBacks.UNSAFE = (Unsafe) field.get(null);
    //     } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
    //         SharedConstants.getLogger().error("Cannot access unsafe class, disabled some feature!");
    //     }
    // }
    //
    // public static void featureCustomBlockHitBoxOverlayFillCallBack(ConfigOption option) {
    //     if (!(Configs.featureCustomBlockHitBoxOverlayFill && Configs.featureCustomBlockHitBoxOverlayOutline)) {
    //         TweakMyClientReference.getConfigHandler().configManager.setValue("breakAnimationMode", BreakAnimationMode.NONE);
    //         reDrawConfigGui(option);
    //     }
    // }
    //
    // public static void featureCustomBlockHitBoxOverlayOutlineCallBack(ConfigOption option) {
    //     if (!(Configs.featureCustomBlockHitBoxOverlayFill && Configs.featureCustomBlockHitBoxOverlayOutline)) {
    //         TweakMyClientReference.getConfigHandler().configManager.setValue("breakAnimationMode", BreakAnimationMode.NONE);
    //         reDrawConfigGui(option);
    //     }
    // }
    //
    // public static void featureCustomWindowTitleCallback(ConfigOption option) {
    //     if (Configs.featureCustomWindowTitle) {
    //         //#if MC >= 11500
    //         CustomWindowHelper.rebuildCache(CustomWindowHelper.TitleType.TITLE);
    //         CustomWindowHelper.rebuildCache(CustomWindowHelper.TitleType.TITLE_WITH_ACTIVITY);
    //         //#else
    //         //$$ CustomWindowUtil.rebuildCache();
    //         //#endif
    //     } else {
    //         CustomWindowHelper.reSetTitle();
    //     }
    // }
    //
    // public static void customWindowTitleEnableActivityCallback(ConfigOption option) {
    //     CallBacks.featureCustomWindowTitleCallback(option);
    //     CallBacks.reDrawConfigGui(option);
    // }
    //
    // public static boolean expNullPointerExceptionTestCallback(KeyAction keyAction, IKeybind iKeybind) {
    //     if (Configs.debugMode && Configs.debugExperimentalMode) {
    //         throw new NullPointerException("Test NullPointerException!");
    //     }
    //
    //     return true;
    // }
    //
    // public static boolean expUnsafeIllegalAllocateTestCallback(KeyAction keyAction, IKeybind iKeybind) {
    //     if (Configs.debugMode && Configs.debugExperimentalMode && CallBacks.UNSAFE != null) {
    //         CallBacks.UNSAFE.putAddress(0, 0);
    //     }
    //
    //     return true;
    // }
}
