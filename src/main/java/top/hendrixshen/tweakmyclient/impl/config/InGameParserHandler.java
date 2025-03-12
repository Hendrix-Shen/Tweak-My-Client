package top.hendrixshen.tweakmyclient.impl.config;

import com.google.common.collect.Sets;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minecraft.client.player.LocalPlayer;
import top.hendrixshen.tweakmyclient.api.event.LocalPlayerListener;

import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InGameParserHandler implements LocalPlayerListener {
    @Getter(lazy = true)
    private static final InGameParserHandler instance = new InGameParserHandler();
    private final Set<ItemStackRestriction> itemStackRestrictions = Sets.newLinkedHashSet();

    @Override
    public void onGameJoin(LocalPlayer localPlayer) {
        this.itemStackRestrictions.forEach(ItemStackRestriction::reParse);
    }

    @Override
    public void onTick(LocalPlayer localPlayer) {

    }

    public void registerItemStackRestriction(ItemStackRestriction restriction) {
        this.itemStackRestrictions.add(restriction);
    }
}
