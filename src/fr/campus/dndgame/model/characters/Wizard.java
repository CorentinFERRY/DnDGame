package fr.campus.dndgame.model.characters;

import fr.campus.dndgame.model.equipments.offensives.Spell;

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
    
    /**
     * Retourne une chaîne décrivant le sort offensif du magicien.
     *
     * @return Informations sur le sort ou message si aucun
     */
    public String getOffensiveInfo() {
        if (spell == null) {
            return "Aucun sort équipée";
        }
        return spell.toString();
    }
}
