package top.hendrixshen.tweakmyclient.mixin.patch.disableLitematicaSchematicVersionCheck;

import fi.dy.masa.litematica.schematic.LitematicaSchematic;
import top.hendrixshen.magiclib.api.dependency.annotation.Dependencies;
import top.hendrixshen.magiclib.api.dependency.annotation.Dependency;
import top.hendrixshen.tweakmyclient.game.Configs;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import top.hendrixshen.magiclib.libs.com.llamalad7.mixinextras.injector.ModifyExpressionValue;

@Dependencies(require = @Dependency(value = "litematica"))
@Mixin(value = LitematicaSchematic.class, remap = false)
public abstract class MixinLitematicaSchematic {
    // The upstream method rejects schematics when their version is outside the supported range
    // (1..5 on MC <= 1.16, 1..6 on MC 1.17-1.20.4, 1..7 on newer MC). Instead of duplicating the
    // whole loading logic (which is fragile across Litematica API changes), widen the two bounds
    // of the `version >= min && version <= max` check so that it always evaluates to true.
    @ModifyExpressionValue(
            //#if MC >= 1.21.11
            //$$ method = "readFromData",
            //#else
            method = "readFromNBT",
            //#endif
            at = @At(
                    value = "CONSTANT",
                    args = "intValue=1",
                    ordinal = 0
            )
    )
    private int disableSchematicVersionLowerBound(int original) {
        return Configs.disableLitematicaSchematicVersionCheck.getBooleanValue() ? Integer.MIN_VALUE : original;
    }

    @ModifyExpressionValue(
            //#if MC >= 1.21.11
            //$$ method = "readFromData",
            //#else
            method = "readFromNBT",
            //#endif
            at = @At(
                    value = "CONSTANT",
                    //#if MC >= 1.20.6
                    //$$ args = "intValue=7",
                    //#else
                    //#if MC >= 1.17.1
                    //$$ args = "intValue=6",
                    //#else
                    args = "intValue=5",
                    //#endif
                    //#endif
                    ordinal = 0
            )
    )
    private int disableSchematicVersionUpperBound(int original) {
        return Configs.disableLitematicaSchematicVersionCheck.getBooleanValue() ? Integer.MAX_VALUE : original;
    }
}
