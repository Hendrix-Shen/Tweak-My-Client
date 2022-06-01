package top.hendrixshen.tweakmyclient.mixin.patch.disableResourcePackCompatCheck;

import net.minecraft.client.Options;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import top.hendrixshen.tweakmyclient.TweakMyClient;

import java.util.List;
// Disable remove incompatible resource pack.
@Mixin(Options.class)
public class MixinOptions {
    @Shadow public List<String> incompatibleResourcePacks;

    @ModifyArg(
            method = "loadSelectedResourcePacks",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;contains(Ljava/lang/Object;)Z",
                    ordinal = 0,
                    remap = false
            )
    )
    private Object on(Object o) {
        this.incompatibleResourcePacks.add((String) o);
        TweakMyClient.getLogger().warn("Prevented the removal of incompatible resource pack {} from options", o);
        return o;
    }
}
