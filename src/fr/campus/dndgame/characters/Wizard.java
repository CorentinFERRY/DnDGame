package fr.campus.dndgame.characters;

import fr.campus.dndgame.equipments.Spell;

public class Wizard extends Character{

    private Spell spell;
    public Wizard (String name){
        super("Wizard",name,6,8);
        this.spell = null;
    }
    //Getter & Setter
    public Spell getSpell(){
        return spell;
    }
    public void setSpell(Spell spell){
        this.spell = spell;
    }
    public String getOffensiveInfo() {
        if (spell == null) {
            return "Aucun sort équipée";
        }
        return spell.toString();
    }
}
