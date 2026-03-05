package fr.campus.dndgame.model.characters;


import fr.campus.dndgame.model.equipments.Weapon;

/**
 * Classe représentant un Guerrier dans le jeu.
 * Un Guerrier possède une arme comme équipement offensif.
 * Caractéristiques : 10 points de santé, 5 points d'attaque.
 * 
 * @author CorentinFERRY
 * @version 1.0
 */
public class Warrior extends Character{

    private Weapon weapon;
    /**
     * Constructeur pour créer un Guerrier.
     *
     * @param name Le nom du guerrier
     */
    public Warrior (String name){
        super("Warrior",name,10,5);
        this.weapon = null;
    }

    // ========== GETTERS & SETTERS ==========
    
    /**
     * Retourne l'arme actuelle du guerrier.
     *
     * @return L'arme équipée, ou null si aucune
     */
    public Weapon getWeapon(){
        return weapon;
    }
    
    /**
     * Définit l'arme du guerrier.
     *
     * @param weapon L'arme à équiper
     */
    public void setWeapon(Weapon weapon){
        this.weapon = weapon;
    }

    /**
     * Retourne une chaîne décrivant l'arme offensif du guerrier.
     *
     * @return Informations sur l'arme ou message si aucune
     */
    public String getOffensiveInfo() {
        if (weapon == null) {
            return "Aucune arme équipée";
        }
        return weapon.toString();
    }
}
