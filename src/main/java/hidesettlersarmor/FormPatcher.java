package hidesettlersarmor;

import necesse.engine.Settings;
import necesse.gfx.forms.Form;
import necesse.gfx.forms.components.FormCheckBox;
import necesse.gfx.forms.components.FormComponent;
import necesse.gfx.forms.events.FormInputEvent;
import necesse.gfx.forms.presets.containerComponent.mob.EquipmentForm;

public class FormPatcher {
    public static int mobId;
    private static final String[] SLOT_NAME = new String[]{"head", "chest", "leg"};
    public static void setAppearance(FormInputEvent<FormCheckBox> e){
        HideSettlersArmor.config.setArmorSlotDisplayState(mobId, ((MyCheckBox)e.from).slot, e.from.checked ? 1 : 0);
    }

    public static void patchForm(EquipmentForm form){
        for (FormComponent component: form.getComponentList()) {
            if (!(component instanceof Form)) continue;
            Form currentForm = (Form)component;
            if (!currentForm.name.equals("settlerequipment")) continue;

            mobId = form.getMob().getUniqueID();
            int xCoord = currentForm.getWidth() / 2 + 37;
            int[] yCoords = new int[]{33, 73, 113};

            MyCheckBox box;
            for (int slot = 0; slot < 3; slot++){
                box = currentForm.addComponent(new MyCheckBox(String.format("Show %s armor", SLOT_NAME[slot]), xCoord, yCoords[slot]));
                box.onClicked(FormPatcher::setAppearance);
                box.checked = HideSettlersArmor.config.getArmorSlotDisplayState(mobId, slot);
                box.slot = slot;

                if (slot == 0) {
                    box.setActive(Settings.showSettlerHeadArmor);
                    if (!Settings.showSettlerHeadArmor) box.checked = false;
                }
            }
            break;
        }
    }
}
