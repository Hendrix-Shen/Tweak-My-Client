package top.hendrixshen.tweakmyclient.mixin.patch.disableLitematicaSchematicVersionCheck;

import fi.dy.masa.litematica.schematic.LitematicaSchematic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import top.hendrixshen.magiclib.dependency.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.annotation.Dependency;
import top.hendrixshen.tweakmyclient.config.Configs;

@Dependencies(and = @Dependency(value = "litematica"))
@Mixin(LitematicaSchematic.class)
public class MixinLitematicaSchematic {
    @ModifyVariable(
            method = "readFromNBT",
            at = @At(
                    value = "STORE"
            )
    )
    private int getNbtVersion(int nbtVersion) {
        return Configs.disableLitematicaSchematicVersionCheck ? 1 : nbtVersion;
    }
}
