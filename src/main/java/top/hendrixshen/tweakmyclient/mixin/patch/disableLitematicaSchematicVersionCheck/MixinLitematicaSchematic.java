package top.hendrixshen.tweakmyclient.mixin.patch.disableLitematicaSchematicVersionCheck;

import fi.dy.masa.litematica.schematic.LitematicaSchematic;
import fi.dy.masa.litematica.schematic.SchematicMetadata;
import net.minecraft.nbt.CompoundTag;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.hendrixshen.magiclib.dependency.annotation.Dependencies;
import top.hendrixshen.magiclib.dependency.annotation.Dependency;
import top.hendrixshen.tweakmyclient.config.Configs;

@Dependencies(and = @Dependency(value = "litematica"))
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
                    target = "Lnet/minecraft/nbt/CompoundTag;getInt(Ljava/lang/String;)I",
                    ordinal = 0
            ),
            cancellable = true
    )
    private void ignoreVersionCheck(CompoundTag nbt, CallbackInfoReturnable<Boolean> cir) {
        if (Configs.disableLitematicaSchematicVersionCheck) {
            this.metadata.readFromNBT(nbt.getCompound("Metadata"));
            this.readSubRegionsFromNBT(nbt.getCompound("Regions"), nbt.getInt("Version"), nbt.getInt("MinecraftDataVersion"));
            cir.setReturnValue(true);
        }
    }
}
