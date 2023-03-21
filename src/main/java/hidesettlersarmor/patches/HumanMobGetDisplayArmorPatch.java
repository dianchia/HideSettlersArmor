package hidesettlersarmor.patches;

import hidesettlersarmor.HideSettlersArmor;
import necesse.engine.Settings;
import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.entity.mobs.friendly.human.GuardHumanMob;
import necesse.entity.mobs.friendly.human.HumanMob;
import necesse.inventory.InventoryItem;
import net.bytebuddy.asm.Advice;

@ModMethodPatch(target = HumanMob.class, name = "getDisplayArmor", arguments = {int.class, InventoryItem.class})
public class HumanMobGetDisplayArmorPatch {
    @Advice.OnMethodEnter(skipOn = Advice.OnNonDefaultValue.class)
    static boolean onEnter() {
        return false;
    }

    @Advice.OnMethodExit
    static void onExit(@Advice.This HumanMob thisHumanMob, @Advice.Argument(0) int slot, @Advice.Argument(1) InventoryItem defaultItem, @Advice.Return(readOnly = false)InventoryItem inventoryItem) {
        if (!thisHumanMob.equipmentInventory.isSlotClear(3 + slot) && thisHumanMob.equipmentInventory.getItemSlot(3 + slot).isArmorItem()) {
            inventoryItem = thisHumanMob.equipmentInventory.getItem(3 + slot);
            return;
        }
        if (!Settings.showSettlerHeadArmor && slot == 0) {
            inventoryItem = defaultItem;
            return;
        }
        inventoryItem = HideSettlersArmor.config.getArmorSlotDisplayState(thisHumanMob.getUniqueID(), slot) ? thisHumanMob.equipmentInventory.getItem(slot) : defaultItem;
    }
}
