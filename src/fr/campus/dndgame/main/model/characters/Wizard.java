package fr.campus.dndgame.main.model.characters;

import fr.campus.dndgame.main.model.equipments.offensives.OffensiveEquipment;
import fr.campus.dndgame.main.model.equipments.offensives.Spell;


/**
 * Classe représentant un Magicien dans le jeu.
 * Un Magicien possède un sort comme équipement offensif.
 * Caractéristiques : 6 points de santé, 8 points d'attaque.
 * 
 * @author CorentinFERRY
 * @version 1.0
 */
public class Wizard extends Character{

    private Spell spell;
    /**
     * Constructeur pour créer un Magicien.
     *
     * @param name Le nom du magicien
     */
    public Wizard (String name){
        super("Wizard",name,6,8);
        this.spell = null;
    }
    
    // ========== GETTERS & SETTERS ==========
    
    /**
     * Retourne le sort actuel du magicien.
     *
     * @return Le sort équipé, ou null si aucun
     */
    public Spell getSpell(){
        return spell;
    }
    
    /**
     * Définit le sort du magicien.
     *
     * @param spell Le sort à équiper
     */
    public void setSpell(Spell spell){
        this.spell = spell;
    }

    @Override
    public OffensiveEquipment getOffensiveEquipment() {
        return spell;
    }

    /**
     * Équipe un nouveau sort si celui-ci est meilleur que celui actuellement équipé.
     * Si un sort est déjà équipé, il sera désarmé et remplacé uniquement si le nouveau est supérieur.
     * Augmente la puissance d'attaque du magicien en fonction du bonus d'attaque du sort.
     * 
     * @param newSpell Le nouveau sort à équiper
     */
    public void equip(Spell newSpell){
        if (this.spell != null){
            if (newSpell.getAttackBonus() <= this.spell.getAttackBonus()){
                return;
            }
            disarm();
        }
        this.setAttack(this.getAttack()+newSpell.getAttackBonus());
        this.spell = newSpell;
    }

    /**
     * Désarme le magicien en supprimant son sort actuel.
     * Réduit la puissance d'attaque du magicien du bonus du sort.
     * N'a aucun effet si aucun sort n'est équipé.
     */
    public void disarm(){
        if (spell != null){
            this.setAttack(this.getAttack()- this.spell.getAttackBonus());
            spell = null;
        }
        if(this.getDefensiveEquipment() != null){
            this.setDefense(0);
            this.setDefensiveEquipment(null);
        }
    }



    /**
     * Retourne une chaîne décrivant le sort offensif du magicien.
     *
     * @return Informations sur le sort ou message si aucun
     */
    public String getOffensiveInfo() {
        if (spell == null) {
            return "Aucun sort équipé";
        }
        return spell.toString();
    }
}
