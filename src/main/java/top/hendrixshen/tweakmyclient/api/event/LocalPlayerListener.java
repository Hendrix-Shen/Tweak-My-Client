package top.hendrixshen.tweakmyclient.api.event;

import top.hendrixshen.magiclib.api.event.Listener;

import net.minecraft.client.player.LocalPlayer;

public interface LocalPlayerListener extends Listener {
    void onGameJoin(LocalPlayer localPlayer);

    void onTick(LocalPlayer localPlayer);
}
