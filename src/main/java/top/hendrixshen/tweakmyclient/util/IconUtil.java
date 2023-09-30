package top.hendrixshen.tweakmyclient.util;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import top.hendrixshen.tweakmyclient.TweakMyClient;
import top.hendrixshen.tweakmyclient.TweakMyClientReference;
import top.hendrixshen.tweakmyclient.config.Configs;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//#if MC < 11903
//$$ import net.minecraft.server.packs.PackType;
//#endif

//#if MC < 11904
//$$ import org.jetbrains.annotations.Nullable;
//$$ import top.hendrixshen.tweakmyclient.TweakMyClientReference;
//$$ import top.hendrixshen.tweakmyclient.mixin.accessor.NativeImageAccessor;
//$$ import java.nio.IntBuffer;
//$$ import java.util.Locale;
//#endif

// Steal from Minecraft Vanilla
public class IconUtil {
    //#if MC > 11902
    private static final String[] VANILLA_STABLE_16X = new String[]{"icons", "icon_16x16.png"};
    private static final String[] VANILLA_STABLE_32X = new String[]{"icons", "icon_32x32.png"};
    //#if MC > 11904
    private static final String[] VANILLA_STABLE_48X = new String[]{"icons", "icon_48x48.png"};
    private static final String[] VANILLA_STABLE_128X = new String[]{"icons", "icon_128x128.png"};
    private static final String[] VANILLA_STABLE_256X = new String[]{"icons", "icon_256x256.png"};
    private static final String[] VANILLA_SNAPSHOT_16X = new String[]{"icons", "snapshot", "icon_16x16.png"};
    private static final String[] VANILLA_SNAPSHOT_32X = new String[]{"icons", "snapshot", "icon_32x32.png"};
    private static final String[] VANILLA_SNAPSHOT_48X = new String[]{"icons", "snapshot", "icon_48x48.png"};
    private static final String[] VANILLA_SNAPSHOT_128X = new String[]{"icons", "snapshot", "icon_128x128.png"};
    private static final String[] VANILLA_SNAPSHOT_256X = new String[]{"icons", "snapshot", "icon_256x256.png"};
    //#endif
    //#else
    //$$ private static final ResourceLocation VANILLA_STABLE_16X = new ResourceLocation("icons/icon_16x16.png");
    //$$ private static final ResourceLocation VANILLA_STABLE_32X = new ResourceLocation("icons/icon_32x32.png");
    //#endif
    private static final ResourceLocation TMC_STABLE_16X = new ResourceLocation(TweakMyClientReference.getModIdentifier(), "texture/icon/icon_16x16.png");
    private static final ResourceLocation TMC_STABLE_32X = new ResourceLocation(TweakMyClientReference.getModIdentifier(), "texture/icon/icon_32x32.png");
    private static final ResourceLocation TMC_STABLE_48X = new ResourceLocation(TweakMyClientReference.getModIdentifier(), "texture/icon/icon_48x48.png");
    private static final ResourceLocation TMC_STABLE_128X = new ResourceLocation(TweakMyClientReference.getModIdentifier(), "texture/icon/icon_128x128.png");
    private static final ResourceLocation TMC_STABLE_256X = new ResourceLocation(TweakMyClientReference.getModIdentifier(), "texture/icon/icon_256x256.png");
    private static final ResourceLocation TMC_SNAPSHOT_16X = new ResourceLocation(TweakMyClientReference.getModIdentifier(), "texture/icon/icon_snapshot_16x16.png");
    private static final ResourceLocation TMC_SNAPSHOT_32X = new ResourceLocation(TweakMyClientReference.getModIdentifier(), "texture/icon/icon_snapshot_32x32.png");
    private static final ResourceLocation TMC_SNAPSHOT_48X = new ResourceLocation(TweakMyClientReference.getModIdentifier(), "texture/icon/icon_snapshot_48x48.png");
    private static final ResourceLocation TMC_SNAPSHOT_128X = new ResourceLocation(TweakMyClientReference.getModIdentifier(), "texture/icon/icon_snapshot_128x128.png");
    private static final ResourceLocation TMC_SNAPSHOT_256X = new ResourceLocation(TweakMyClientReference.getModIdentifier(), "texture/icon/icon_snapshot_256x256.png");

    private static void setIcon(ArrayList<InputStream> inputStreams) throws IOException {
        //#if MC > 11701
        RenderSystem.assertInInitPhase();
        //#elseif MC > 11404
        //$$ RenderSystem.assertThread(RenderSystem::isInInitPhase);
        //#endif

        ArrayList<ByteBuffer> byteBuffers = new ArrayList<>(inputStreams.size());

        try (MemoryStack memoryStack = MemoryStack.stackPush()) {
            //#if MC > 11802
            GLFWImage.Buffer buffer = GLFWImage.malloc(inputStreams.size(), memoryStack);
            //#else
            //$$ GLFWImage.Buffer buffer = GLFWImage.mallocStack(inputStreams.size(), memoryStack);
            //#endif

            for (int i = 0; i < inputStreams.size(); i++) {
                try (NativeImage image = NativeImage.read(inputStreams.get(i))) {
                    ByteBuffer byteBuffer = MemoryUtil.memAlloc(image.getWidth() * image.getHeight() * 4);
                    byteBuffers.add(byteBuffer);
                    byteBuffer.asIntBuffer().put(IconUtil.getPixelsRGBA(image));
                    buffer.position(i);
                    buffer.width(image.getWidth());
                    buffer.height(image.getHeight());
                    buffer.pixels(byteBuffer);
                }
            }

            GLFW.glfwSetWindowIcon(TweakMyClient.getMinecraftClient().getWindowCompat().getWindow(), buffer.position(0));
        } finally {
            byteBuffers.forEach(MemoryUtil::memFree);
        }
    }

    @Contract("_ -> new")
    private static int @NotNull [] getPixelsRGBA(@NotNull NativeImage nativeImage) {
        //#if MC > 11903
        return nativeImage.getPixelsRGBA();
        //#else
        //$$ NativeImageAccessor accessor = MiscUtil.cast(nativeImage);
        //$$
        //$$ if (accessor.getFormat() != NativeImage.Format.RGBA) {// 232
        //$$     throw new IllegalArgumentException(String.format(Locale.ROOT, "getPixelsRGBA only works on RGBA images; have %s", accessor.getFormat()));
        //$$ } else {
        //$$     accessor.invokeCheckAllocated();
        //$$     int[] is = new int[nativeImage.getWidth() * nativeImage.getHeight()];
        //$$     MemoryUtil.memIntBuffer(accessor.getPixels(), nativeImage.getWidth() * nativeImage.getHeight()).get(is);
        //$$     return is;
        //$$ }
        //#endif
    }

    public static void updateIcon() {
        Minecraft mc = TweakMyClient.getMinecraftClient();
        ArrayList<InputStream> inputStreams = new ArrayList<>(5);

        try {
            if (Configs.featureCustomWindowIcon) {
                IconUtil.pushResource(inputStreams, TMC_STABLE_16X, TMC_SNAPSHOT_16X);
                IconUtil.pushResource(inputStreams, TMC_STABLE_32X, TMC_SNAPSHOT_32X);
                IconUtil.pushResource(inputStreams, TMC_STABLE_48X, TMC_SNAPSHOT_48X);
                IconUtil.pushResource(inputStreams, TMC_STABLE_128X, TMC_SNAPSHOT_128X);
                IconUtil.pushResource(inputStreams, TMC_STABLE_256X, TMC_SNAPSHOT_256X);
            } else {
                //#if MC > 11904
                IconUtil.pushVanillaResource(inputStreams, IconUtil.VANILLA_STABLE_16X, IconUtil.VANILLA_SNAPSHOT_16X);
                IconUtil.pushVanillaResource(inputStreams, IconUtil.VANILLA_STABLE_32X, IconUtil.VANILLA_SNAPSHOT_32X);
                IconUtil.pushVanillaResource(inputStreams, IconUtil.VANILLA_STABLE_48X, IconUtil.VANILLA_SNAPSHOT_48X);
                IconUtil.pushVanillaResource(inputStreams, IconUtil.VANILLA_STABLE_128X, IconUtil.VANILLA_SNAPSHOT_128X);
                IconUtil.pushVanillaResource(inputStreams, IconUtil.VANILLA_STABLE_256X, IconUtil.VANILLA_SNAPSHOT_256X);
                //#elseif MC > 11902
                //$$ inputStreams.add(Objects.requireNonNull(mc.getVanillaPackResources().getRootResource(IconUtil.VANILLA_STABLE_16X)).get());
                //$$ inputStreams.add(Objects.requireNonNull(mc.getVanillaPackResources().getRootResource(IconUtil.VANILLA_STABLE_32X)).get());
                //#else
                //$$ inputStreams.add(mc.getClientPackSource().getVanillaPack().getResource(PackType.CLIENT_RESOURCES, IconUtil.VANILLA_STABLE_16X));
                //$$ inputStreams.add(mc.getClientPackSource().getVanillaPack().getResource(PackType.CLIENT_RESOURCES, IconUtil.VANILLA_STABLE_32X));
                //#endif
            }

            IconUtil.setIcon(inputStreams);
        } catch (IOException | NullPointerException e) {
            TweakMyClientReference.getLogger().error("Couldn't set icon");
        }
    }

    private static void pushResource(@NotNull ArrayList<InputStream> list, ResourceLocation stable,
                                     ResourceLocation snapshot) throws IOException {
        Minecraft mc = TweakMyClient.getMinecraftClient();
        list.add(
                mc.getResourceManager().getResource(SharedConstants.getCurrentVersion().isStable() ? stable : snapshot)
                //#if MC > 11802
                .orElseThrow(RuntimeException::new).open()
                //#else
                //$$ .getInputStream()
                //#endif
        );
    }

    //#if MC > 11904
    private static void pushVanillaResource(@NotNull ArrayList<InputStream> list, String[] stable,
                                            String[] snapshot) throws IOException {
        Minecraft mc = TweakMyClient.getMinecraftClient();
        list.add(Objects.requireNonNull(mc.getVanillaPackResources()
                .getRootResource(SharedConstants.getCurrentVersion().isStable() ? stable : snapshot)).get());
    }
    //#endif
}
