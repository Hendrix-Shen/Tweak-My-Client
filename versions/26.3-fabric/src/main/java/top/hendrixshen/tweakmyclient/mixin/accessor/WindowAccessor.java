package top.hendrixshen.tweakmyclient.mixin.accessor;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.server.packs.resources.IoSupplier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

// CHECKSTYLE.OFF: JavadocStyle
/**
 * <li>mc1.14 ~ mc26.2: subproject 1.16.5 (main project) [dummy]</li>
 * <li>mc26.3+        : subproject 26.3        &lt;--------</li>
 */
// CHECKSTYLE.ON: JavadocStyle
@Mixin(Window.class)
public interface WindowAccessor {
    @Invoker("setIcon")
    void tmc$setIcon(List<IoSupplier<InputStream>> iconStreams) throws IOException;
}
