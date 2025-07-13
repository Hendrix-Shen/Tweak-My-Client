package top.hendrixshen.tweakmyclient.impl.feature.autoReconnect;

import com.google.common.collect.Maps;
import top.hendrixshen.magiclib.api.compat.minecraft.client.gui.components.ButtonCompat;
import top.hendrixshen.magiclib.api.compat.minecraft.client.gui.screen.ScreenCompat;
import top.hendrixshen.magiclib.api.compat.minecraft.network.chat.ComponentCompat;
import top.hendrixshen.magiclib.util.ReflectionUtil;
import top.hendrixshen.magiclib.util.collect.ValueContainer;
import top.hendrixshen.tweakmyclient.SharedConstants;
import top.hendrixshen.tweakmyclient.SharedConstants.Mods;
import top.hendrixshen.tweakmyclient.game.Configs;

// CHECKSTYLE.OFF: ImportOrder
//#if MC > 11904
//$$ import org.jetbrains.annotations.NotNull;
//#endif
// CHECKSTYLE.ON: ImportOrder

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

// CHECKSTYLE.OFF: ImportOrder
//#if 12000 > MC && MC > 11502
import com.mojang.blaze3d.vertex.PoseStack;
//#endif

//#if MC > 11904
//$$ import net.minecraft.network.chat.CommonComponents;
//#endif

//#if MC > 11502
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.MultiLineLabel;
//#endif
// CHECKSTYLE.ON: ImportOrder

import java.util.LinkedHashMap;

// CHECKSTYLE.OFF: ImportOrder
//#if MC < 11600
//$$ import java.util.List;
//#endif
// CHECKSTYLE.ON: ImportOrder

public class PatchedDisconnectedScreen extends Screen {
    //#if MC > 11502
    private MultiLineLabel message = MultiLineLabel.EMPTY;
    //#else
    //$$ private List<String> lines;
    //#endif
    private final Component reason;
    private final Screen parent;
    private int textHeight;
    private Button autoReconnectButton;
    private int reconnectTimer = Configs.autoReconnectInterval.getIntegerValue() * 20;
    private final LinkedHashMap<String, Screen> modHashMap = Maps.newLinkedHashMap();

    public PatchedDisconnectedScreen(Screen parent, Component title, Component reason) {
        super(title);
        this.reason = reason;
        this.parent = parent;
    }

    @Override
    protected void init() {
        this.initModMap();
        //#if MC > 11502
        this.message = MultiLineLabel.create(this.font, this.reason, this.width - 50);
        this.textHeight = this.message.getLineCount() * 9;
        //#else
        //$$ this.lines = this.font.split(this.reason.getColoredString(), this.width - 50);
        //$$ this.textHeight = this.lines.size() * 9;
        //#endif
        int backButtonX = width / 2 - 100;
        int backButtonY = Math.min(this.height / 2 + this.textHeight / 2 + 9, this.height - 30);
        Minecraft mc = Minecraft.getInstance();
        Button backButton = ButtonCompat.builder(
                        ComponentCompat.translatable("gui.toMenu"), button -> mc.setScreen(this.parent))
                .bounds(backButtonX, backButtonY, 200, 20)
                .build();
        Button staticButton = ButtonCompat.builder(
                        ComponentCompat.literal(SharedConstants.tr("feature.autoReconnect.gui.button.reconnect")),
                        button -> AutoReconnectUtil.reconnect(this.parent))
                .bounds(backButtonX, backButtonY + 24, 98, 20)
                .build();
        this.autoReconnectButton = ButtonCompat.builder(
                        ComponentCompat.literal(SharedConstants.tr("feature.autoReconnect.gui.button.switcher.disabled")),
                        this::onPressAutoReconnect)
                .bounds(backButtonX + 102, backButtonY + 24, 98, 20)
                .build();
        ScreenCompat screen = ScreenCompat.of(this);
        screen.addButton(backButton);
        screen.addButton(staticButton);
        screen.addButton(this.autoReconnectButton);

        if (this.reason == null
                //#if MC > 11902
                //$$ || AutoReconnectUtil.RE_AUTH_MESSAGES.stream().anyMatch(component -> component.getString().equals(this.reason.getString()))
                //#else
                || AutoReconnectUtil.getTranslationKey(reason).startsWith("disconnect.loginFailed")
                //#endif
        ) {
            Configs.autoReconnect.setBooleanValue(false);

            if (this.modHashMap.isEmpty()) {
                return;
            }

            int offsetX = 0;
            int buttonWidth = (200 - 4 * (this.modHashMap.size() - 1)) / this.modHashMap.size();

            for (String modId : this.modHashMap.keySet()) {
                screen.addButton(ButtonCompat.builder(
                                ComponentCompat.literal(SharedConstants.tr(String.format("feature.autoReconnect.gui.button.authenticate.%s", modId))),
                                button -> mc.setScreen(this.modHashMap.get(modId)))
                        .pos(backButtonX + offsetX, 48 + backButtonY)
                        .size(buttonWidth, 20).build());
                offsetX += buttonWidth + 4;
            }
        }
    }

    //#if MC > 11904
    //$$ @Override
    //$$ public @NotNull Component getNarrationMessage() {
    //$$     return CommonComponents.joinForNarration(this.title, this.reason);
    //$$ }
    //#endif

    @Override
    public void render(
            //#if MC > 11904
            //$$ GuiGraphics guiGraphicsOrPoseStack,
            //#elseif MC > 11502
            PoseStack guiGraphicsOrPoseStack,
            //#endif
            int mouseX,
            int mouseY,
            float partialTick
    ) {
        //#if MC >= 12002
        //$$ // NO-OP
        //#elseif MC > 11502
        this.renderBackground(
                // CHECKSTYLE.OFF: NoWhitespaceBefore
                // CHECKSTYLE.OFF: SeparatorWrap
                //#if MC > 11502
                guiGraphicsOrPoseStack
                //#endif
                //#if MC > 12001
                //$$ , mouseX
                //$$ , mouseY
                //$$ , partialTick
                //#endif
                // CHECKSTYLE.ON: SeparatorWrap
                // CHECKSTYLE.ON: NoWhitespaceBefore
        );
        //#else
        //$$ this.renderBackground();
        //#endif

        super.render(
                //#if MC > 11502
                guiGraphicsOrPoseStack,
                //#endif
                mouseX,
                mouseY,
                partialTick
        );

        //#if MC > 11502
        //#if MC > 11904
        //$$ guiGraphicsOrPoseStack.drawCenteredString(this.font, this.title, this.width / 2, this.height / 2 - this.textHeight / 2 - 9 * 2, 0xAAAAAA);
        //#else
        GuiComponent.drawCenteredString(guiGraphicsOrPoseStack, this.font, this.title, this.width / 2, this.height / 2 - this.textHeight / 2 - 9 * 2, 0xAAAAAA);
        //#endif
        this.message.renderCentered(guiGraphicsOrPoseStack, this.width / 2, this.height / 2 - this.textHeight / 2);
        //#else
        //$$ this.drawCenteredString(this.font, this.title.getColoredString(), this.width / 2, this.height / 2 - this.textHeight / 2 - 9 * 2, 0xAAAAAA);
        //$$ int k = this.height / 2 - this.textHeight / 2;
        //$$
        //$$ if (this.lines != null) {
        //$$     for (String string : this.lines) {
        //$$         this.drawCenteredString(this.font, string, this.width / 2, k, 0xFFFFFF);
        //$$         k += 9;
        //$$     }
        //$$ }
        //#endif
    }

    @Override
    public void tick() {
        if (!Configs.autoReconnect.getBooleanValue()) {
            this.autoReconnectButton.setMessage(
                    //#if MC > 11502
                    ComponentCompat.literal(SharedConstants.tr("feature.autoReconnect.gui.button.switcher.disabled"))
                    //#else
                    //$$ SharedConstants.tr("feature.autoReconnect.gui.button.switcher.disabled")
                    //#endif
            );
            return;
        }

        this.autoReconnectButton.setMessage(
                //#if MC > 11502
                ComponentCompat.literal(SharedConstants.tr("feature.autoReconnect.gui.button.switcher.timer"))
                //#else
                //$$ SharedConstants.tr("feature.autoReconnect.gui.button.switcher.timer")
                //#endif
        );

        if (this.reconnectTimer > 0) {
            this.reconnectTimer--;
            return;
        }

        AutoReconnectUtil.reconnect(parent);
    }

    private void initModMap() {
        if (Mods.AUTH_ME.isLoaded()) {
            ValueContainer<Screen> screen = ReflectionUtil.newInstance("me.axieum.mcmod.authme.impl.gui.AuthMethodScreen", new Class[]{Screen.class}, parent);

            if (!screen.isPresent()) {
                screen = ReflectionUtil.newInstance("me.axieum.mcmod.authme.gui.AuthScreen", new Class[]{Screen.class}, parent);
            }

            screen.ifPresentOrElse(
                    s -> this.modHashMap.put(SharedConstants.Mods.AUTH_ME.getIdentifier(), s),
                    () -> SharedConstants.getLogger().warn("Unable to create AuthMe screen instance.")
            );
        }

        if (Mods.IN_GAME_ACCOUNT_SWITCHER.isLoaded()) {
            ValueContainer<Screen> screen = ReflectionUtil.newInstance("ru.vidtu.ias.screen.AccountScreen", new Class[]{Screen.class}, parent);

            if (!screen.isPresent()) {
                screen = ReflectionUtil.newInstance("the_fireplace.ias.gui.GuiAccountSelector", new Class[]{Screen.class}, parent);
            }

            if (!screen.isPresent()) {
                screen = ReflectionUtil.newInstance("the_fireplace.ias.gui.AccountListScreen", new Class[]{Screen.class}, parent);
            }

            screen.ifPresentOrElse(
                    s -> this.modHashMap.put(SharedConstants.Mods.IN_GAME_ACCOUNT_SWITCHER.getIdentifier(), s),
                    () -> SharedConstants.getLogger().warn("Unable to create In-Game-Account-Switcher screen instance.")
            );
        }

        if (Mods.OAUTH.isLoaded()) {
            ValueContainer<Screen> screen = ReflectionUtil.newInstance("com.sintinium.oauthfabric.gui.profile.ProfileSelectionScreen", new Class[]{null}, (Object) null);

            if (!screen.isPresent()) {
                screen = ReflectionUtil.newInstance("com.sintinium.oauth.oauthfabric.gui.LoginTypeScreen", new Class[]{Screen.class}, parent);
            }

            screen.ifPresentOrElse(
                    s -> this.modHashMap.put(Mods.OAUTH.getIdentifier(), s),
                    () -> SharedConstants.getLogger().warn("Unable to create OAuth screen instance.")
            );
        }

        if (Mods.RE_AUTH.isLoaded()) {
            ValueContainer<Screen> screen = ReflectionUtil.newInstance("technicianlp.reauth.gui.AuthScreen", new Class[]{Screen.class}, parent);

            screen.ifPresentOrElse(
                    s -> this.modHashMap.put(Mods.RE_AUTH.getIdentifier(), s),
                    () -> SharedConstants.getLogger().warn("Unable to create ReAuth screen instance.")
            );
        }
    }

    private void onPressAutoReconnect(Button button) {
        Configs.autoReconnect.setBooleanValue(!Configs.autoReconnect.getBooleanValue());

        if (Configs.autoReconnect.getBooleanValue()) {
            this.reconnectTimer = Configs.autoReconnectInterval.getIntegerValue() * 20;
        }
    }
}
