package fr.campus.dndgame.equipments;

import fr.campus.dndgame.characters.Character;

/**
 * Classe représentant un bouclier, un équipement défensif.
 * Un bouclier augmente la défense du personnage qui l'utilise.
 * 
 * @author CorentinFERRY
 * @version 1.0
 */
public class Shield extends DefensiveEquipment {
    private int defenseBonus;

    /**
     * Crée un nouveau bouclier.
     *
     * @param name         nom du bouclier
     * @param defenseBonus bonus de défense accordé
     */
    public Shield(String name, int defenseBonus) {
        super("Shield", name);
        this.defenseBonus = defenseBonus;
    }

    // ========== GETTERS & SETTERS ==========
    /**
     * Retourne le bonus de défense du bouclier.
     *
     * @return valeur du bonus de défense
     */
    public int getDefenseBonus() {
        return defenseBonus;
    }
    /**
     * Modifie le bonus de défense du bouclier.
     *
     * @param defenseBonus nouvelle valeur du bonus de défense
     */
    public void setDefenseBonus(int defenseBonus) {
        this.defenseBonus = defenseBonus;
    }

    /**
     * Applique l'effet du bouclier au personnage et affiche un message d'action.
     *
     * @param character personnage qui utilise le bouclier
     */
    @Override
    public void use(Character character) {
        System.out.println(character.getName() + " utilise le bouclier " + getName() +
                " et gagne " + defenseBonus + " points de défense.");
    }

    @Override
    public String toString() {
        return super.toString() + " (" + defenseBonus + " de defense bonus)";
    }
}
