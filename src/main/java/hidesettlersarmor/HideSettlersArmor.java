package hidesettlersarmor;

import necesse.engine.modLoader.annotations.ModEntry;

@ModEntry
public class HideSettlersArmor {
    public static Config config;
    public void preInit() {
        config = new Config();
    }
}
