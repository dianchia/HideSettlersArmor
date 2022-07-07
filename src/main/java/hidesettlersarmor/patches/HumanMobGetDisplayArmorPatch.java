package hidesettlersarmor.patches;

import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.entity.mobs.friendly.human.GuardHumanMob;
import necesse.entity.mobs.friendly.human.HumanMob;
import necesse.inventory.InventoryItem;
import net.bytebuddy.asm.Advice;

@ModMethodPatch(target = HumanMob.class, name = "getDisplayArmor", arguments = {int.class, String.class})
public class HumanMobGetDisplayArmorPatch {
    @Advice.OnMethodEnter(skipOn = Advice.OnNonDefaultValue.class)
    static boolean onEnter() {
        return true;
    }

    @Advice.OnMethodExit
    static void onExit(@Advice.This HumanMob thisHumanMob, @Advice.Argument(0) int slot, @Advice.Argument(1) String defaultItemStringID, @Advice.Return(readOnly = false)InventoryItem inventoryItem) {
        if (!thisHumanMob.equipmentInventory.isSlotClear(3 + slot) && thisHumanMob.equipmentInventory.getItemSlot(3 + slot).isArmorItem()) {
            inventoryItem = thisHumanMob.equipmentInventory.getItem(slot + 3);
        } else {
            if (thisHumanMob instanceof GuardHumanMob) {
                inventoryItem = thisHumanMob.getDisplayArmor(slot, (InventoryItem) null);
            } else {
                inventoryItem = defaultItemStringID == null ? null : new InventoryItem(defaultItemStringID);
            }
        }
    }
}
