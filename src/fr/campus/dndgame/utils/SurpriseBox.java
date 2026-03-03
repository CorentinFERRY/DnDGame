package fr.campus.dndgame.utils;

import fr.campus.dndgame.equipments.Equipment;

public class SurpriseBox {
    private Equipment equipment;

    public SurpriseBox(Equipment equipment){
        this.equipment = equipment;
    }

    public Equipment getEquipment() {
        return equipment;
    }
    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    @Override
    public String toString() {
        return equipment.toString();
    }
}
