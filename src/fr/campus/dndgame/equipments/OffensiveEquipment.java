package fr.campus.dndgame.equipments;

public abstract class OffensiveEquipment extends Equipment{

    private final int attackBonus;

    protected OffensiveEquipment(String type,String name, int attackBonus){
        super(type,name);
        this.attackBonus = attackBonus;
    }

    //Getter & Setter
    public int getAttackBonus(){
        return attackBonus;
    }

    //Methode toString()
    public String toString(){
        return " (Attaque: " + attackBonus + ")";
    }
}
