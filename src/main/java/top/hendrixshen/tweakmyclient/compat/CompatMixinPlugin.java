/*
 * Copyright (c) Copyright 2020 - 2022 The Cat Town Craft and contributors.
 * This source code is subject to the terms of the GNU Lesser General Public
 * License, version 3. If a copy of the LGPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/lgpl-3.0.txt
 */
package top.hendrixshen.tweakmyclient.compat;

import com.google.common.collect.Lists;
import net.fabricmc.tinyremapper.IMappingProvider.Member;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.transformer.meta.MixinMerged;
import org.spongepowered.asm.util.Annotations;
import top.hendrixshen.tweakmyclient.TweakMyClientMixinPlugin;
import top.hendrixshen.tweakmyclient.util.RemapUtil;
import top.hendrixshen.tweakmyclient.util.mixin.MixinType;
import top.hendrixshen.tweakmyclient.util.mixin.annotation.MagicAttack;
import top.hendrixshen.tweakmyclient.util.mixin.annotation.MagicInterruption;

import java.util.ArrayList;
import java.util.List;

public class CompatMixinPlugin extends TweakMyClientMixinPlugin {
    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
        super.postApply(targetClassName, targetClass, mixinClassName, mixinInfo);

        // Magical chanting.
        ClassNode mixinClass = mixinInfo.getClassNode(0);
        AnnotationNode magicInterruption = Annotations.getVisible(mixinClass, MagicInterruption.class);

        if (magicInterruption == null) {
            return;
        }

        for (MethodNode methodNode : mixinClass.methods) {
            AnnotationNode magicAttack = Annotations.getVisible(methodNode, MagicAttack.class);

            if (magicAttack == null) {
                continue;
            }

            this.jikuTsuiho(targetClass, methodNode, magicInterruption, magicAttack);
        }
    }

    /**
     * 時空追放.
     * <p>Erase target.
     * @param targetClass Injection target class.
     * @param magicAttackMethodNode Spell casting MethodNode.
     * @param magicInterruption Spell casting interruption AnnotationNode.
     * @param magicAttack Magical attacking AnnotationNode.
     */
    private void jikuTsuiho(ClassNode targetClass, MethodNode magicAttackMethodNode, AnnotationNode magicInterruption, AnnotationNode magicAttack) {
        List<Class<?>> tekitaiClass = Annotations.getValue(magicInterruption, "value", true);
        List<String> tekitaiTarget = Annotations.getValue(magicInterruption, "targets", true);

        ArrayList<String> tekitaiClasses = Lists.newArrayList();

        for (Class<?> cls : tekitaiClass) {
            String s = cls.getName();
            if (!tekitaiClasses.contains(s)) {
                tekitaiClasses.add(s);
            }
        }

        for (String s : tekitaiTarget) {
            if (!tekitaiClasses.contains(s)) {
                tekitaiClasses.add(s);
            }
        }

        MethodNode tekitaiMethod = this.findTekitaiMethod(targetClass, magicAttack, tekitaiClasses);

        if (tekitaiMethod == null) {
            return;
        }

        // Magic releasing.
        this.tsuihoTarget(targetClass, tekitaiMethod, magicAttackMethodNode, magicAttack);
        // Magic Harvesting.
        targetClass.visibleAnnotations.removeIf(annotationNode -> annotationNode.desc.equals("Ltop/hendrixshen/tweakmyclient/util/mixin/annotation/MagicInterruption;"));
    }

    /**
     * Find the enemy's MethodNode.
     * @param targetClass Injection target class.
     * @param magicAttack Spell casting MethodNode.
     * @param tekitaiTarget Enemy's class.
     * @return Enemy's MethodNode.
     */
    private @Nullable MethodNode findTekitaiMethod(@NotNull ClassNode targetClass, AnnotationNode magicAttack, ArrayList<String> tekitaiTarget) {
        List<MixinType> type = Annotations.getValue(magicAttack, "type", true, MixinType.class);
        String name = Annotations.getValue(magicAttack, "name");
        int priority = Annotations.getValue(magicAttack, "priority", 1000);

        for (MethodNode methodNode : targetClass.methods) {
            if (!(methodNode.name.contains(type.get(0).getPrefix()) && methodNode.name.contains(name))) {
                continue;
            }

            AnnotationNode annotationNode = Annotations.getVisible(methodNode, MixinMerged.class);
            String mixin = Annotations.getValue(annotationNode, "mixin");

            if (!tekitaiTarget.contains(mixin)) {
                continue;
            }

            int targetPriority = Annotations.getValue(annotationNode, "priority");

            if (priority != targetPriority) {
                continue;
            }

            return methodNode;
        }

        return null;
    }

    /**
     * Erase target and infuse soul.
     * @param targetClass Injection target ClassNode.
     * @param tekitaiMethodNode Enemy's MethodNode.
     * @param magicAttackMethodNode Spell casting MethodNode.
     * @param magicAttack Magical attacking AnnotationNode.
     */
    private void tsuihoTarget(@NotNull ClassNode targetClass, MethodNode tekitaiMethodNode, MethodNode magicAttackMethodNode, AnnotationNode magicAttack) {
        String method = Annotations.getValue(magicAttack, "method");
        String owner = Annotations.getValue(magicAttack, "owner");
        String desc = Annotations.getValue(magicAttack, "desc");
        Member remappedMethod = RemapUtil.mapMethod(owner, method, desc);
        int ordinal = Annotations.getValue(magicAttack, "ordinal", -1);
        boolean keep = Annotations.getValue(magicAttack, "keep", Boolean.FALSE);

        for (MethodNode methodNode : targetClass.methods) {
            if (methodNode.name.equals(remappedMethod.name) && methodNode.desc.equals(remappedMethod.desc)) {
                int offset = 0;
                int found = 0;
                int processed = 0;

                for(AbstractInsnNode abstractInsnNode : methodNode.instructions) {
                    if (!(abstractInsnNode instanceof MethodInsnNode)) {
                        continue;
                    }

                    MethodInsnNode methodInsnNode = (MethodInsnNode) abstractInsnNode;

                    if (!methodInsnNode.name.equals(tekitaiMethodNode.name) ||
                            !methodInsnNode.desc.equals(tekitaiMethodNode.desc)) {
                        continue;
                    }

                    if ((tekitaiMethodNode.access & Opcodes.ACC_STATIC) == Opcodes.ACC_STATIC &&
                            methodInsnNode.getOpcode() != Opcodes.INVOKESTATIC) {
                        continue;
                    }

                    if ((tekitaiMethodNode.access & Opcodes.ACC_STATIC) == 0 &&
                            methodInsnNode.getOpcode() == Opcodes.INVOKESTATIC) {
                        continue;
                    }

                    if (ordinal < 0 || ordinal == offset) {
                        MethodInsnNode invokeMethod = new MethodInsnNode(methodInsnNode.getOpcode(), methodInsnNode.owner,
                                magicAttackMethodNode.name, methodInsnNode.desc);
                        methodNode.instructions.insertBefore(methodInsnNode, invokeMethod);
                        methodNode.instructions.remove(methodInsnNode);
                        processed++;
                    }

                    offset++;
                    found++;

                    if (found == processed && !keep) {
                        targetClass.methods.remove(tekitaiMethodNode);
                    }
                }

                break;
            }
        }
    }
}
