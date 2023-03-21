package hidesettlersarmor.patches;

import hidesettlersarmor.HideSettlersArmor;
import hidesettlersarmor.MyCheckBox;
import necesse.engine.GameLog;
import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.friendly.human.HumanMob;
import necesse.gfx.forms.components.FormCheckBox;
import necesse.gfx.forms.components.FormFlow;
import necesse.gfx.forms.events.FormInputEvent;
import necesse.gfx.forms.presets.containerComponent.settlement.equipment.SettlementEquipmentContentBox;
import necesse.inventory.container.settlement.data.SettlementSettlerData;
import necesse.inventory.container.settlement.data.SettlementSettlerEquipmentFilterData;
import necesse.level.maps.levelData.settlementData.settler.SettlerMob;
import net.bytebuddy.asm.Advice;

import java.util.Iterator;

@ModMethodPatch(target = SettlementEquipmentContentBox.class, name = "updateEquipmentsContent", arguments = {})
public class SettlementEquipmentContentBoxUpdateEquipmentContentPatch {
    public static int mobId;
    @Advice.OnMethodEnter
    static void OnMethodEnter() {
        GameLog.debug.println("Patching updateEquipmentsContent of class SettlementEquipmentContentBox");
    }

    @Advice.OnMethodExit
    static void OnMethodExit(@Advice.This SettlementEquipmentContentBox thisBox, @Advice.FieldValue("var4") Iterator<SettlementSettlerData> settlers) {
        FormFlow flow = new FormFlow(0);
        MyCheckBox box;
        while (settlers.hasNext()){
            SettlementSettlerEquipmentFilterData data = (SettlementSettlerEquipmentFilterData) settlers.next();
            SettlerMob settlerMob = data.getSettlerMob(thisBox.equipmentsForm.client.getLevel());
            if (settlerMob == null) continue;
            Mob mob = settlerMob.getMob();
            if (!(mob instanceof HumanMob)) continue;

            final HumanMob humanMob = (HumanMob) mob;
            mobId = humanMob.getUniqueID();

            int y = flow.next(32);
            int buttonX = thisBox.getWidth() - 24 - thisBox.getScrollBarWidth() - 2;
            buttonX -= (24 * 3) + 4 + (32 * 3) + (4 * 2);

            buttonX -= 32;
            box = thisBox.addComponent(new MyCheckBox("", buttonX + 8, y + 4));
            box.onClicked(SettlementEquipmentContentBoxUpdateEquipmentContentPatch::setAppearance);
            box.checked = HideSettlersArmor.config.getArmorSlotDisplayState(mobId, 2);
            box.slot = 2;

            buttonX -= 32;
            box = thisBox.addComponent(new MyCheckBox("", buttonX + 8, y + 4));
            box.onClicked(SettlementEquipmentContentBoxUpdateEquipmentContentPatch::setAppearance);
            box.checked = HideSettlersArmor.config.getArmorSlotDisplayState(mobId, 1);
            box.slot = 1;

            buttonX -= 32;
            box = thisBox.addComponent(new MyCheckBox("", buttonX + 8, y + 4));
            box.onClicked(SettlementEquipmentContentBoxUpdateEquipmentContentPatch::setAppearance);
            box.checked = HideSettlersArmor.config.getArmorSlotDisplayState(mobId, 0);
            box.slot = 0;
        }
        GameLog.debug.println("Finished...");
    }

    static void setAppearance(FormInputEvent<FormCheckBox> e) {
        HideSettlersArmor.config.setArmorSlotDisplayState(mobId, ((MyCheckBox)e.from).slot, e.from.checked ? 1 : 0);
    }
}
