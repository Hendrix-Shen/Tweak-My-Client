/*
 * Copyright (c) Copyright 2020 - 2022 The Cat Town Craft and contributors.
 * This source code is subject to the terms of the GNU Lesser General Public
 * License, version 3. If a copy of the LGPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/lgpl-3.0.txt
 */
package top.hendrixshen.tweakmyclient.util.mixin;

public enum MixinType {
    // ModifyVariable
    MODIFY_ARG("modify"),
    MODIFY_ARGS("args"),
    MODIFY_CONSTANT("constant"),
    MODIFY_VARIABLE("localvar"),
    REDIRECT("redirect");

    private final String prefix;

    MixinType(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
}
