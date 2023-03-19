package top.hendrixshen.tweakmyclient.util;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;
import net.fabricmc.tinyremapper.IMappingProvider.Member;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RemapUtil {
    private static final Pattern CLASS_FINDER = Pattern.compile("Lnet\\/minecraft\\/([^;]+);");
    private static final String INTERMEDIARY = "intermediary";
    private static final MappingResolver RESOLVER = FabricLoader.getInstance().getMappingResolver();

    private static String fromIntermediaryDot(String className) {
        return RESOLVER.mapClassName(INTERMEDIARY, String.format("net.minecraft.%s", className));
    }

    public static @NotNull String getClassName(String className) {
        return RemapUtil.fromIntermediaryDot(className).replace('.', '/');
    }

    public static String getMethodName(String owner, String methodName, String desc) {
        return RemapUtil.RESOLVER.mapMethodName(INTERMEDIARY, "net.minecraft." + owner, methodName, desc);
    }

    @Contract("_, _, _ -> new")
    public static @NotNull Member mapMethod(String owner, String name, String desc) {
        return new Member(RemapUtil.getClassName(owner), RemapUtil.getMethodName(owner, name, desc), RemapUtil.mapMethodDescriptor(desc));
    }

    public static @NotNull String mapMethodDescriptor(String desc) {
        StringBuilder stringBuffer = new StringBuilder();

        Matcher matcher = CLASS_FINDER.matcher(desc);

        while (matcher.find()) {
            matcher.appendReplacement(stringBuffer, Matcher.quoteReplacement('L' + RemapUtil.getClassName(matcher.group(1)) + ';'));
        }

        return matcher.appendTail(stringBuffer).toString();
    }
}
