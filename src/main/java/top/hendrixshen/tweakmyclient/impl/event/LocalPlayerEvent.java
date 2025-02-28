package top.hendrixshen.tweakmyclient.impl.event;

import lombok.AllArgsConstructor;
import net.minecraft.client.player.LocalPlayer;
import top.hendrixshen.magiclib.api.event.Event;
import top.hendrixshen.tweakmyclient.api.event.LocalPlayerListener;

import java.util.List;

public class LocalPlayerEvent {
    @AllArgsConstructor
    public static class LocalPlayerTickEvent implements Event<LocalPlayerListener> {
        private final LocalPlayer player;

        @Override
        public void dispatch(List<LocalPlayerListener> list) {
            list.forEach(listener -> listener.onTick(this.player));
        }

        @Override
        public Class<LocalPlayerListener> getListenerType() {
            return LocalPlayerListener.class;
        }
    }
}
