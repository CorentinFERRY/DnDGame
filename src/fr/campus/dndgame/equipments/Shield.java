package fr.campus.dndgame.equipments;

import fr.campus.dndgame.characters.Character;

public class Shield extends DefensiveEquipment{
    private int defenseBonus;

    public Shield(String name,int defenseBonus){
        super("Shield",name);
        this.defenseBonus = defenseBonus;
    }
    //Getters & Setters
    public int getDefenseBonus() {
        return defenseBonus;
    }
    public void setDefenseBonus(int defenseBonus) {
        this.defenseBonus = defenseBonus;
    }
    @Override
    public void use(Character character) {
        System.out.println(character.getName() + " utilise le bouclier " + getName() +
                " et gagne " + defenseBonus + " points de défense.");
    }
}
