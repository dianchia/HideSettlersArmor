package hidesettlersarmor;

import necesse.engine.GameLog;
import necesse.engine.GlobalData;
import necesse.engine.save.LoadData;
import necesse.engine.save.SaveData;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Config {
    public Map<Integer, Integer> idToState;

    public Config(){
        this.idToState = new HashMap<>();
        File file = new File(GlobalData.cfgPath() + "dianchia_hidesettlersarmor.cfg");
        if (!file.exists()) return;

        LoadData config = new LoadData(file);
        for (LoadData data : config.getLoadData()){
            this.idToState.put(Integer.parseInt(data.getName()), config.getInt(data.getName()));
        }
    }

    public void setArmorSlotDisplayState(int mobId, int slot, int state){
        int newState = idToState.getOrDefault(mobId, 0);
        newState = state == 1 ? newState | (1 << slot) : newState & ~(1 << slot);
        idToState.put(mobId, newState);
        saveConfig();
        GameLog.debug.printf("Setting display state for mob %d: Slot %d -> State %d%n", mobId, slot, state);
        GameLog.debug.printf("New State for mob %d : %d%n", mobId, newState);
    }

    public boolean getArmorSlotDisplayState(int uniqueId, int slot){
        return (idToState.getOrDefault(uniqueId, 0) & (1 << slot)) != 0;
    }

    public void saveConfig() {
        SaveData data = new SaveData("HideSettlersArmor");
        for (int mobId: idToState.keySet()) {
            data.addInt(String.valueOf(mobId), idToState.get(mobId));
        }
        File cfgFile = new File(GlobalData.cfgPath() + "dianchia_hidesettlersarmor.cfg");
        data.saveScript(cfgFile);
    }
}
