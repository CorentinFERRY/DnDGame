package fr.campus.dndgame.equipments;

import fr.campus.dndgame.characters.Character;

public class Potion extends DefensiveEquipment{

    private int healAmount;

    public Potion(String name, int healAmount) {
        super("Potion", name);
        this.healAmount = healAmount;
    }

    //Getters & Setters
    public int getHealAmount(){
        return healAmount;
    }
    public void setHealAmount(int healAmount){
        this.healAmount =healAmount;
    }

    @Override
    public void use(Character character){
        int newHealth = character.getHealth() + healAmount;
        if (newHealth > character.getMaxHealth()) {
            newHealth = character.getMaxHealth();
        }
        character.setHealth(newHealth);
        System.out.println(character.getName() + " utilise " + getName() + " et récupère " + healAmount + " PV ! " +
                "PV actuels : " + character.getHealth());
    }


}
