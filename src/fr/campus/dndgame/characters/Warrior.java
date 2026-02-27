package fr.campus.dndgame.characters;


import fr.campus.dndgame.equipments.Weapon;

public class Warrior extends Character{

    private Weapon weapon;
    public Warrior (String name){
        super("Warrior",name,10,5);
        this.weapon = null;
    }

    //Getter & Setter
    public Weapon getWeapon(){
        return weapon;
    }
    public void setWeapon(Weapon weapon){
        this.weapon = weapon;
    }

    public String getOffensiveInfo() {
        if (weapon == null) {
            return "Aucune arme équipée";
        }
        return weapon.toString();
    }
}
