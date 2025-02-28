package top.hendrixshen.tweakmyclient.api.event;

import net.minecraft.client.player.LocalPlayer;
import top.hendrixshen.magiclib.api.event.Listener;

public interface LocalPlayerListener extends Listener {
    void onTick(LocalPlayer localPlayer);
}
