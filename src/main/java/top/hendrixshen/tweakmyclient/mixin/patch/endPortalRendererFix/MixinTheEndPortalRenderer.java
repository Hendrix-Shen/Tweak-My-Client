package top.hendrixshen.tweakmyclient.mixin.patch.endPortalRendererFix;

import top.hendrixshen.tweakmyclient.game.Configs;
import top.hendrixshen.tweakmyclient.impl.patch.endPortalRendererFix.EndPortalRenderMode;

// CHECKSTYLE.OFF: ImportOrder
//#if MC >= 11903
//$$ import org.joml.Matrix4f;
//#endif
// CHECKSTYLE.ON: ImportOrder

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.blockentity.TheEndPortalRenderer;
import net.minecraft.core.Direction;

// CHECKSTYLE.OFF: ImportOrder
//#if MC < 12110
import net.minecraft.world.level.block.entity.TheEndPortalBlockEntity;
//#endif

//#if MC < 11903
import com.mojang.math.Matrix4f;
//#endif
// CHECKSTYLE.ON: ImportOrder

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.magiclib.libs.com.llamalad7.mixinextras.sugar.Local;

// CHECKSTYLE.OFF: ImportOrder
//#if MC >= 12110
//$$ import java.util.EnumSet;
//#endif
// CHECKSTYLE.ON: ImportOrder

// CHECKSTYLE.OFF: JavadocStyle
/**
 * <li>mc1.14            : subproject 1.14.4</li>
 * <li>mc1.15 ~ mc1.21.11: subproject 1.16.5 (main project)        &lt;--------</li>
 * <li>mc26.1+           : subproject 26.1.2 [dummy]</li>
 */
// CHECKSTYLE.ON: JavadocStyle
@Mixin(TheEndPortalRenderer.class)
public abstract class MixinTheEndPortalRenderer {
    //#if MC < 26.1
    @Shadow
    protected abstract void renderFace(
            //#if MC >= 12110
            //$$ EnumSet<Direction> enumSetOrTheEndPortalBlockEntity,
            //#else
            TheEndPortalBlockEntity enumSetOrTheEndPortalBlockEntity,
            //#endif
            Matrix4f matrix4f,
            VertexConsumer vertexConsumer,
            float x0,
            float x1,
            float y0,
            float y1,
            float z0,
            float z1,
            float z2,
            float z3,
            //#if MC < 11700
            float colorR,
            float colorG,
            float colorB,
            //#endif
            Direction direction
    );

    @Inject(
            method = "renderCube",
            at = @At(
                    value = "INVOKE",
                    //#if MC >= 12110
                    //$$ target = "Lnet/minecraft/client/renderer/blockentity/AbstractEndPortalRenderer;renderFace(Ljava/util/EnumSet;Lorg/joml/Matrix4f;Lcom/mojang/blaze3d/vertex/VertexConsumer;FFFFFFFFLnet/minecraft/core/Direction;)V",
                    //#elseif MC > 11902
                    //$$ target = "Lnet/minecraft/client/renderer/blockentity/TheEndPortalRenderer;renderFace(Lnet/minecraft/world/level/block/entity/TheEndPortalBlockEntity;Lorg/joml/Matrix4f;Lcom/mojang/blaze3d/vertex/VertexConsumer;FFFFFFFFLnet/minecraft/core/Direction;)V",
                    //#elseif MC > 11605
                    //$$ target = "Lnet/minecraft/client/renderer/blockentity/TheEndPortalRenderer;renderFace(Lnet/minecraft/world/level/block/entity/TheEndPortalBlockEntity;Lcom/mojang/math/Matrix4f;Lcom/mojang/blaze3d/vertex/VertexConsumer;FFFFFFFFLnet/minecraft/core/Direction;)V",
                    //#else
                    target = "Lnet/minecraft/client/renderer/blockentity/TheEndPortalRenderer;renderFace(Lnet/minecraft/world/level/block/entity/TheEndPortalBlockEntity;Lcom/mojang/math/Matrix4f;Lcom/mojang/blaze3d/vertex/VertexConsumer;FFFFFFFFFFFLnet/minecraft/core/Direction;)V",
                    //#endif
                    ordinal = 0
            ),
            cancellable = true
    )
    private void onRenderCube(
            //#if MC >= 12110
            //$$ EnumSet<Direction> enumSetOrBlockEntity,
            //#else
            TheEndPortalBlockEntity enumSetOrBlockEntity,
            //#endif
            //#if MC < 11700
            float offset,
            float factor,
            //#endif
            Matrix4f matrix4f,
            VertexConsumer consumer,
            CallbackInfo ci,
            //#if MC > 11605
            //$$ @Local(ordinal = 0) float offsetDown,
            //$$ @Local(ordinal = 1) float offsetUp
            //#else
            @Local(ordinal = 2) float colorR,
            @Local(ordinal = 3) float colorG,
            @Local(ordinal = 4) float colorB
            //#endif
    ) {
        if (!Configs.endPortalRendererFix.getBooleanValue()) {
            return;
        }

        if (Configs.endPortalRenderMode.getOptionListValue() == EndPortalRenderMode.ACTUAL) {
            // Rendering the ender portal using its hit box.
            //#if MC > 11605
            //$$ this.renderFace(enumSetOrBlockEntity, matrix4f, consumer, 0.0F, 1.0F, offsetDown, offsetUp, 1.0F, 1.0F, 1.0F, 1.0F, Direction.SOUTH);
            //$$ this.renderFace(enumSetOrBlockEntity, matrix4f, consumer, 0.0F, 1.0F, offsetUp, offsetDown, 0.0F, 0.0F, 0.0F, 0.0F, Direction.NORTH);
            //$$ this.renderFace(enumSetOrBlockEntity, matrix4f, consumer, 1.0F, 1.0F, offsetUp, offsetDown, 0.0F, 1.0F, 1.0F, 0.0F, Direction.EAST);
            //$$ this.renderFace(enumSetOrBlockEntity, matrix4f, consumer, 0.0F, 0.0F, offsetDown, offsetUp, 0.0F, 1.0F, 1.0F, 0.0F, Direction.WEST);
            //$$ this.renderFace(enumSetOrBlockEntity, matrix4f, consumer, 0.0F, 1.0F, offsetDown, offsetDown, 0.0F, 0.0F, 1.0F, 1.0F, Direction.DOWN);
            //$$ this.renderFace(enumSetOrBlockEntity, matrix4f, consumer, 0.0F, 1.0F, offsetUp, offsetUp, 1.0F, 1.0F, 0.0F, 0.0F, Direction.UP);
            //#else
            this.renderFace(enumSetOrBlockEntity, matrix4f, consumer, 0.0F, 1.0F, 0.0F, offset, 1.0F, 1.0F, 1.0F, 1.0F, colorR, colorG, colorB, Direction.SOUTH);
            this.renderFace(enumSetOrBlockEntity, matrix4f, consumer, 0.0F, 1.0F, offset, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, colorR, colorG, colorB, Direction.NORTH);
            this.renderFace(enumSetOrBlockEntity, matrix4f, consumer, 1.0F, 1.0F, offset, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, colorR, colorG, colorB, Direction.EAST);
            this.renderFace(enumSetOrBlockEntity, matrix4f, consumer, 0.0F, 0.0F, 0.0F, offset, 0.0F, 1.0F, 1.0F, 0.0F, colorR, colorG, colorB, Direction.WEST);
            this.renderFace(enumSetOrBlockEntity, matrix4f, consumer, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, colorR, colorG, colorB, Direction.DOWN);
            this.renderFace(enumSetOrBlockEntity, matrix4f, consumer, 0.0F, 1.0F, offset, offset, 1.0F, 1.0F, 0.0F, 0.0F, colorR, colorG, colorB, Direction.UP);
            //#endif
        } else if (Configs.endPortalRenderMode.getOptionListValue() == EndPortalRenderMode.FULL) {
            // Rendering the end portal as a full block.
            //#if MC > 11605
            //$$ this.renderFace(enumSetOrBlockEntity, matrix4f, consumer, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, Direction.SOUTH);
            //$$ this.renderFace(enumSetOrBlockEntity, matrix4f, consumer, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, Direction.NORTH);
            //$$ this.renderFace(enumSetOrBlockEntity, matrix4f, consumer, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, Direction.EAST);
            //$$ this.renderFace(enumSetOrBlockEntity, matrix4f, consumer, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, Direction.WEST);
            //$$ this.renderFace(enumSetOrBlockEntity, matrix4f, consumer, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, Direction.DOWN);
            //$$ this.renderFace(enumSetOrBlockEntity, matrix4f, consumer, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, Direction.UP);
            //#else
            this.renderFace(enumSetOrBlockEntity, matrix4f, consumer, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, colorR, colorG, colorB, Direction.SOUTH);
            this.renderFace(enumSetOrBlockEntity, matrix4f, consumer, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, colorR, colorG, colorB, Direction.NORTH);
            this.renderFace(enumSetOrBlockEntity, matrix4f, consumer, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, colorR, colorG, colorB, Direction.EAST);
            this.renderFace(enumSetOrBlockEntity, matrix4f, consumer, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, colorR, colorG, colorB, Direction.WEST);
            this.renderFace(enumSetOrBlockEntity, matrix4f, consumer, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, colorR, colorG, colorB, Direction.DOWN);
            this.renderFace(enumSetOrBlockEntity, matrix4f, consumer, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, colorR, colorG, colorB, Direction.UP);
            //#endif
        } else if (Configs.endPortalRenderMode.getOptionListValue() == EndPortalRenderMode.LEGACY) {
            // Rendering the end portal with Minecraft 21w13a below.
            //#if MC > 11605
            //$$ this.renderFace(enumSetOrBlockEntity, matrix4f, consumer, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, Direction.SOUTH);
            //$$ this.renderFace(enumSetOrBlockEntity, matrix4f, consumer, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, Direction.NORTH);
            //$$ this.renderFace(enumSetOrBlockEntity, matrix4f, consumer, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, Direction.EAST);
            //$$ this.renderFace(enumSetOrBlockEntity, matrix4f, consumer, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, Direction.WEST);
            //$$ this.renderFace(enumSetOrBlockEntity, matrix4f, consumer, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, Direction.DOWN);
            //$$ this.renderFace(enumSetOrBlockEntity, matrix4f, consumer, 0.0F, 1.0F, offsetUp, offsetUp, 1.0F, 1.0F, 0.0F, 0.0F, Direction.UP);
            //#else
            this.renderFace(enumSetOrBlockEntity, matrix4f, consumer, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, colorR, colorG, colorB, Direction.SOUTH);
            this.renderFace(enumSetOrBlockEntity, matrix4f, consumer, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, colorR, colorG, colorB, Direction.NORTH);
            this.renderFace(enumSetOrBlockEntity, matrix4f, consumer, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, colorR, colorG, colorB, Direction.EAST);
            this.renderFace(enumSetOrBlockEntity, matrix4f, consumer, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, colorR, colorG, colorB, Direction.WEST);
            this.renderFace(enumSetOrBlockEntity, matrix4f, consumer, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, colorR, colorG, colorB, Direction.DOWN);
            this.renderFace(enumSetOrBlockEntity, matrix4f, consumer, 0.0F, 1.0F, offset, offset, 1.0F, 1.0F, 0.0F, 0.0F, colorR, colorG, colorB, Direction.UP);
            //#endif
        } else if (Configs.endPortalRenderMode.getOptionListValue() == EndPortalRenderMode.MODERN) {
            // Rendering the end portal with Minecraft 21w13a and above.
            //#if MC > 11605
            //$$ this.renderFace(enumSetOrBlockEntity, matrix4f, consumer, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, Direction.SOUTH);
            //$$ this.renderFace(enumSetOrBlockEntity, matrix4f, consumer, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, Direction.NORTH);
            //$$ this.renderFace(enumSetOrBlockEntity, matrix4f, consumer, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, Direction.EAST);
            //$$ this.renderFace(enumSetOrBlockEntity, matrix4f, consumer, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, Direction.WEST);
            //$$ this.renderFace(enumSetOrBlockEntity, matrix4f, consumer, 0.0F, 1.0F, offsetDown, offsetDown, 0.0F, 0.0F, 1.0F, 1.0F, Direction.DOWN);
            //$$ this.renderFace(enumSetOrBlockEntity, matrix4f, consumer, 0.0F, 1.0F, offsetUp, offsetUp, 1.0F, 1.0F, 0.0F, 0.0F, Direction.UP);
            //#else
            this.renderFace(enumSetOrBlockEntity, matrix4f, consumer, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, colorR, colorG, colorB, Direction.SOUTH);
            this.renderFace(enumSetOrBlockEntity, matrix4f, consumer, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, colorR, colorG, colorB, Direction.NORTH);
            this.renderFace(enumSetOrBlockEntity, matrix4f, consumer, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, colorR, colorG, colorB, Direction.EAST);
            this.renderFace(enumSetOrBlockEntity, matrix4f, consumer, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, colorR, colorG, colorB, Direction.WEST);
            this.renderFace(enumSetOrBlockEntity, matrix4f, consumer, 0.0F, 1.0F, 0.375F, 0.375F, 0.0F, 0.0F, 1.0F, 1.0F, colorR, colorG, colorB, Direction.DOWN);
            this.renderFace(enumSetOrBlockEntity, matrix4f, consumer, 0.0F, 1.0F, offset, offset, 1.0F, 1.0F, 0.0F, 0.0F, colorR, colorG, colorB, Direction.UP);
            //#endif
        }

        ci.cancel();
    }
    //#endif
}
