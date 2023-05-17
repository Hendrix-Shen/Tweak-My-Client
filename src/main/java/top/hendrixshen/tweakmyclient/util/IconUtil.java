package top.hendrixshen.tweakmyclient.util;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
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
    private static final String[] VANILLA_STABLE_48X = new String[]{"icons", "icon_48x48.png"};
    //#else
    //$$ private static final ResourceLocation VANILLA_STABLE_16X = new ResourceLocation("icons/icon_16x16.png");
    //$$ private static final ResourceLocation VANILLA_STABLE_32X = new ResourceLocation("icons/icon_32x32.png");
    //#endif
    private static final ResourceLocation TMC_STABLE_16X = new ResourceLocation(TweakMyClientReference.getModIdentifier(), "texture/icon/icon_16x16.png");
    private static final ResourceLocation TMC_STABLE_32X = new ResourceLocation(TweakMyClientReference.getModIdentifier(), "texture/icon/icon_32x32.png");
    private static final ResourceLocation TMC_STABLE_48X = new ResourceLocation(TweakMyClientReference.getModIdentifier(), "texture/icon/icon_48x48.png");

    private static void setIcon(ArrayList<InputStream> inputStreams) throws IOException {
        //#if MC > 11701
        RenderSystem.assertInInitPhase();
        //#elseif MC > 11404
        //$$ RenderSystem.assertThread(RenderSystem::isInInitPhase);
        //#endif

        if (Minecraft.ON_OSX) {
            // TODO: MAC_OS support, Low Priority because I'm lazy，meow~
            return;
        }

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
        // TODO: 1.20 support.
        //#if MC < 12000
        Minecraft mc = TweakMyClient.getMinecraftClient();
        ArrayList<InputStream> inputStreams = new ArrayList<>(3);

        try {
            if (Configs.featureCustomWindowIcon) {
                //#if MC > 11802
                inputStreams.add(mc.getResourceManager().getResource(IconUtil.TMC_STABLE_16X).orElseThrow(RuntimeException::new).open());
                inputStreams.add(mc.getResourceManager().getResource(IconUtil.TMC_STABLE_32X).orElseThrow(RuntimeException::new).open());
                inputStreams.add(mc.getResourceManager().getResource(IconUtil.TMC_STABLE_48X).orElseThrow(RuntimeException::new).open());
                //#else
                //$$ inputStreams.add(mc.getResourceManager().getResource(IconUtil.TMC_STABLE_16X).getInputStream());
                //$$ inputStreams.add(mc.getResourceManager().getResource(IconUtil.TMC_STABLE_32X).getInputStream());
                //#endif
            } else {
                //#if MC > 11902
                inputStreams.add(Objects.requireNonNull(mc.getVanillaPackResources().getRootResource(IconUtil.VANILLA_STABLE_16X)).get());
                inputStreams.add(Objects.requireNonNull(mc.getVanillaPackResources().getRootResource(IconUtil.VANILLA_STABLE_32X)).get());
                //#else
                //$$ inputStreams.add(mc.getClientPackSource().getVanillaPack().getResource(PackType.CLIENT_RESOURCES, IconUtil.VANILLA_STABLE_16X));
                //$$ inputStreams.add(mc.getClientPackSource().getVanillaPack().getResource(PackType.CLIENT_RESOURCES, IconUtil.VANILLA_STABLE_32X));
                //#endif
            }

            IconUtil.setIcon(inputStreams);
        } catch (IOException | NullPointerException e) {
            TweakMyClientReference.getLogger().error("Couldn't set icon");
        }
        //#endif
    }
}