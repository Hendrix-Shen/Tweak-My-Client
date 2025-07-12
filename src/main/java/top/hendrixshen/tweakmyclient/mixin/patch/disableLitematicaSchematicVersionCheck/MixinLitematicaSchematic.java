package top.hendrixshen.tweakmyclient.mixin.patch.disableLitematicaSchematicVersionCheck;

import fi.dy.masa.litematica.schematic.LitematicaSchematic;
import fi.dy.masa.litematica.schematic.SchematicMetadata;
import net.minecraft.nbt.CompoundTag;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.hendrixshen.magiclib.api.dependency.annotation.Dependencies;
import top.hendrixshen.magiclib.api.dependency.annotation.Dependency;
import top.hendrixshen.tweakmyclient.game.Configs;

//#if MC >= 12105
//$$ import fi.dy.masa.litematica.config.Configs.Generic;
//$$ import fi.dy.masa.litematica.util.FileType;
//#endif

@Dependencies(require = @Dependency(value = "litematica"))
@Mixin(LitematicaSchematic.class)
public abstract class MixinLitematicaSchematic {
    @Shadow(remap = false)
    @Final
    private SchematicMetadata metadata;

    @Shadow
    protected abstract void readSubRegionsFromNBT(CompoundTag tag, int version, int minecraftDataVersion);

    @Inject(
            method = "readFromNBT",
            at = @At(
                    value = "INVOKE",
                    //#if MC >= 12105
                    //$$ target = "Lnet/minecraft/nbt/CompoundTag;getIntOr(Ljava/lang/String;I)I",
                    //#else
                    target = "Lnet/minecraft/nbt/CompoundTag;getInt(Ljava/lang/String;)I",
                    //#endif
                    ordinal = 0
            ),
            cancellable = true
    )
    private void ignoreVersionCheck(CompoundTag nbt, CallbackInfoReturnable<Boolean> cir) {
        if (Configs.disableLitematicaSchematicVersionCheck.getBooleanValue()) {
            //#if MC >= 12105
            //$$ int version = nbt.getIntOr("Version", -1);
            //$$ int minecraftDataVersion = nbt.getIntOr("MinecraftDataVersion", Generic.DATAFIXER_DEFAULT_SCHEMA.getIntegerValue());
            //$$ this.metadata.readFromNBT(nbt.getCompoundOrEmpty("Metadata"));
            //$$ this.metadata.setSchematicVersion(version);
            //$$ this.metadata.setMinecraftDataVersion(minecraftDataVersion);
            //$$ this.metadata.setFileType(FileType.LITEMATICA_SCHEMATIC);
            //$$ this.readSubRegionsFromNBT(nbt.getCompoundOrEmpty("Regions"), version, minecraftDataVersion);
            //#else
            this.metadata.readFromNBT(nbt.getCompound("Metadata"));
            this.readSubRegionsFromNBT(nbt.getCompound("Regions"), nbt.getInt("Version"), nbt.getInt("MinecraftDataVersion"));
            //#endif
            cir.setReturnValue(true);
        }
    }
}
