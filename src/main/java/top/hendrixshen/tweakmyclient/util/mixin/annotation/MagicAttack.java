/*
 * Copyright (c) Copyright 2020 - 2022 The Cat Town Craft and contributors.
 * This source code is subject to the terms of the GNU Lesser General Public
 * License, version 3. If a copy of the LGPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/lgpl-3.0.txt
 */
package top.hendrixshen.tweakmyclient.util.mixin.annotation;

import top.hendrixshen.tweakmyclient.util.mixin.MixinType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * You need magic to defeat magic.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MagicAttack {
    /**
     * The type of injector you want to erase the target.
     * @return Injector Type.
     */
    MixinType type();

    /**
     * Name of the method you want to erase the target.
     * @return Target method name.
     */
    String name();

    /**
     * From which owner you want to erase the target. Here you should use the intermediary name.
     * @return Source method owner.
     */
    String owner();

    /**
     * From which method you want to erase the target. Here you should use the intermediary name.
     * @return Source method name.
     */
    String method();

    /**
     * From which method you want to erase the target. Here you should use the intermediary name.
     * @return Source method desc.
     */
    String desc();

    /**
     * The point at which you want to erase the target.
     * @return Offset(-1 means all).
     */
    int ordinal() default -1;

    /**
     * Priority of the method you want to erase the target.
     * @return Priority.
     */
    int priority() default 1000;

    /**
     * Whether to retain the target original injection.
     * @return Whether to keep.
     */
    boolean keep() default false;
}
