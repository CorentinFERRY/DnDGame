package fr.campus.dndgame.main.model.equipments.defensives;

import fr.campus.dndgame.main.model.characters.Character;
import fr.campus.dndgame.main.utils.Menu;

/**
 * Classe représentant un bouclier, un équipement défensif.
 * Un bouclier augmente la défense du personnage qui l'utilise.
 * 
 * @author CorentinFERRY
 * @version 1.0
 */
public class Shield extends DefensiveEquipment {

    /**
     * Crée un nouveau bouclier.
     *
     * @param name         nom du bouclier
     * @param defenseBonus bonus de défense accordé
     */
    public Shield(String name, int defenseBonus) {
        super("Shield", name);
        setEffect(defenseBonus);
    }

    // ========== GETTERS & SETTERS ==========
    /**
     * Retourne le bonus de défense du bouclier.
     *
     * @return valeur du bonus de défense
     */
    public int getDefenseBonus() {
        return getEffect();
    }

    /**
     * Modifie le bonus de défense du bouclier.
     *
     * @param defenseBonus nouvelle valeur du bonus de défense
     */
    public void setDefenseBonus(int defenseBonus) {
        setEffect(defenseBonus);
    }

    /**
     * Applique l'effet du bouclier au personnage et affiche un message d'action.
     *
     * @param character personnage qui utilise le bouclier
     */
    @Override
    public void use(Character character, Menu menu) {
        character.equipDefensiveEquipment(this);
        menu.showMessage(character.getName() + " utilise le bouclier " + getName() +
                " et gagne " + getEffect() + " points de défense.");
    }

    /**
     * Retourne une représentation textuelle du bouclier.
     *
     * @return Une chaîne décrivant le bonus de defense
     */
    @Override
    public String toString() {
        return "Shield : Bouclier (Augmente la défense de: " + getEffect() + ")";
    }
}
