//#if FORGE
//$$
//$$ package top.hendrixshen.tweakmyclient;
//$$
//$$ import top.hendrixshen.magiclib.api.entrypoint.ModInitializer;
//$$ import top.hendrixshen.magiclib.util.minecraft.ForgePlatformUtil;
//$$ import top.hendrixshen.tweakmyclient.game.ConfigGui;
//$$
//$$ import net.minecraftforge.fml.common.Mod;
//$$
//$$ @Mod("@MOD_IDENTIFIER@")
//$$ public class TweakMyClientForge implements ModInitializer {
//$$     public TweakMyClientForge() {
//$$         this.construct();
//$$     }
//$$
//$$     @Override
//$$     public void onInitializeClient() {
//$$         TweakMyClient.getInstance().onInitializeClient();
//$$         TweakMyClientForge.setupForgeConfigGui();
//$$     }
//$$
//$$     @Override
//$$     public void onInitializeServer() {
//$$     }
//$$
//$$     @Override
//$$     public void onInitialize() {
//$$     }
//$$
//$$     private static void setupForgeConfigGui() {
//$$         ForgePlatformUtil.registerModConfigScreen(SharedConstants.getModIdentifier(),
//$$                 screen -> {
//$$                     ConfigGui gui = new ConfigGui();
//$$                     //#if MC > 11903
//$$                     gui.setParent(screen);
//$$                     //#else
//$$                     //$$ gui.setParentGui(screen);
//$$                     //#endif
//$$                     return gui;
//$$                 });
//$$     }
//$$ }
//#endif
