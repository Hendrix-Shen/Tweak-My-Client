package top.hendrixshen.tweakmyclient.mixin.feature.featureDaylightOverride;

import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
//#if MC >= 11500
import net.minecraft.client.multiplayer.ClientLevel;
//#else
//$$ import net.minecraft.client.multiplayer.MultiPlayerLevel;
//#endif
import net.minecraft.client.renderer.LevelRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import top.hendrixshen.tweakmyclient.config.Configs;

@Mixin(LevelRenderer.class)
public class MixinLevelRenderer {
    //#if MC >= 11500
    @Shadow
    private ClientLevel level;
    //#else
    //$$ @Shadow
    //$$ private MultiPlayerLevel level;
    //#endif

    @ModifyArg(
            method = "renderSky",
            at = @At(
                    value = "INVOKE",
                    //#if MC >= 11600
                    target = "Lnet/minecraft/client/renderer/DimensionSpecialEffects;getSunriseColor(FF)[F"
                    //#else
                    //$$ target = "Lnet/minecraft/world/level/dimension/Dimension;getSunriseColor(FF)[F"
                    //#endif
            ),
            index = 0
    )
    private float onGetSunriseColor(float i) {
        //#if MC >= 11600
        return Configs.featureDaylightOverride ? this.level.dimensionType().timeOfDay(Configs.daylightOverrideTime) : i;
        //#else
        //$$ return Configs.featureDaylightOverride ? this.level.getTimeOfDay(Configs.daylightOverrideTime) : i;
        //#endif
    }

    @ModifyArg(
            method = "renderSky",
            at = @At(
                    value = "INVOKE",
                    //#if MC >= 11500
                    target = "Lcom/mojang/blaze3d/vertex/PoseStack;mulPose(Lcom/mojang/math/Quaternion;)V",
                    //#else
                    //$$ target = "Lcom/mojang/blaze3d/platform/GlStateManager;rotatef(FFFF)V",
                    //#endif
                    ordinal = 4
            ),
            index = 0
    )
    //#if MC >= 11600
    private Quaternion modifyDayTime(Quaternion i) {
        return Configs.featureDaylightOverride ? Vector3f.XP.rotationDegrees(this.level.dimensionType().timeOfDay(Configs.daylightOverrideTime) * 360.0F) : i;
    //#elseif MC >= 11500
    //$$ private Quaternion modifyDayTime(Quaternion i) {
    //$$ return Configs.featureDaylightOverride ? Vector3f.XP.rotationDegrees(this.level.getTimeOfDay(Configs.daylightOverrideTime) * 360.0F) : i;
    //#else
    //$$ private float modifyDayTime(float i) {
    //$$ return Configs.featureDaylightOverride ? this.level.getTimeOfDay(Configs.daylightOverrideTime) * 360.0F : i;
    //#endif
    }
}
