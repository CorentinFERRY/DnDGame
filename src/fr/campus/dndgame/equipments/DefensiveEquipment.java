package fr.campus.dndgame.equipments;

import fr.campus.dndgame.characters.Character;

public abstract class DefensiveEquipment extends Equipment{

    protected DefensiveEquipment(String type,String name){
        super(type,name);
    }

    public abstract void use(Character character);
}
