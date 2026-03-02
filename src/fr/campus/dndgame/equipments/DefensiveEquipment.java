package fr.campus.dndgame.equipments;

public class DefensiveEquipment extends Equipment{
    private final int effect;

    protected DefensiveEquipment(String type,String name, int effect){
        super(type,name);
        this.effect = effect;
    }

    //Getter & Setter
    public int getEffect(){
        return effect;
    }

    //Methode toString()
    public String toString(){
        return super.toString() + " (Effet: " + effect + ")";
    }
}
