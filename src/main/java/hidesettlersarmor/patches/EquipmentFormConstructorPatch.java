package hidesettlersarmor.patches;

import hidesettlersarmor.FormPatcher;
import necesse.engine.modLoader.annotations.ModConstructorPatch;
import necesse.engine.network.client.Client;
import necesse.gfx.forms.events.FormEventListener;
import necesse.gfx.forms.presets.containerComponent.mob.EquipmentForm;
import necesse.gfx.forms.presets.containerComponent.mob.ShopEquipmentForm;
import necesse.inventory.container.Container;
import net.bytebuddy.asm.Advice;

@ModConstructorPatch(target = EquipmentForm.class, arguments = {Client.class, Container.class, String.class, int.class, int.class, int.class, int.class, int.class, int.class, int.class, FormEventListener.class}) // No arguments
public class EquipmentFormConstructorPatch {
//    @Advice.OnMethodExit
    static void OnMethodExit(@Advice.This EquipmentForm form) {
        if (!(form instanceof ShopEquipmentForm)) {
            FormPatcher.patchForm(form);
        }
    }
}
