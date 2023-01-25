package hidesettlersarmor.patches;

import hidesettlersarmor.FormPatcher;
import necesse.engine.modLoader.annotations.ModConstructorPatch;
import necesse.engine.network.client.Client;
import necesse.gfx.forms.events.FormEventListener;
import necesse.gfx.forms.presets.containerComponent.mob.ShopEquipmentForm;
import necesse.inventory.container.mob.ShopContainer;
import net.bytebuddy.asm.Advice;

@ModConstructorPatch(target = ShopEquipmentForm.class, arguments = {Client.class, ShopContainer.class, FormEventListener.class})
public class ShopEquipmentFormPatch {
    @Advice.OnMethodExit
    static void OnMethodExit(@Advice.This ShopEquipmentForm form) {
        FormPatcher.patchForm(form);
    }
}