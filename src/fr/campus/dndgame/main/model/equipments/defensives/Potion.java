package fr.campus.dndgame.main.model.equipments.defensives;

import fr.campus.dndgame.main.model.characters.Character;
import fr.campus.dndgame.main.utils.Menu;

/**
 * Classe représentant une potion, un équipement défensif.
 * Une potion restaure les points de vie du personnage qui l'utilise.
 * 
 * @author CorentinFERRY
 * @version 1.0
 */
public class Potion extends DefensiveEquipment {

    /**
     * Constructeur pour créer une potion.
     *
     * @param name       Le nom de la potion
     * @param healAmount Le nombre de points de vie restaurés par la potion
     */
    public Potion(String name, int healAmount) {
        super("Potion", name);
        setEffect(healAmount);
    }

    // ========== GETTERS & SETTERS ==========

    /**
     * Retourne la quantité de points de vie restaurés par la potion.
     *
     * @return Le montant de guérison
     */
    public int getHealAmount() {
        return getEffect();
    }

    /**
     * Définit la quantité de points de vie restaurés par la potion.
     *
     * @param healAmount Nouveau montant de guérison
     */
    public void setHealAmount(int healAmount) {
        setEffect(healAmount);
    }

    /**
     * Utilise la potion pour guérir le personnage.
     * Affiche un message indiquant les PV restaurés et l'état actuel.
     *
     * @param character Le personnage qui utilise la potion
     */
    @Override
    public void use(Character character, Menu menu) {
        int newHealth = character.getHealth() + getEffect();
        if (newHealth > character.getMaxHealth()) {
            newHealth = character.getMaxHealth();
        }
        character.setHealth(newHealth);
        menu.showMessage(character.getName() + " utilise " + getName() + " et récupère " + getEffect() + " PV ! " +
                "PV actuels : " + character.getHealth());
    }

    /**
     * Retourne une représentation textuelle de la potion.
     *
     * @return Une chaîne décrivant le montant de soin
     */
    @Override
    public String toString() {
        return super.toString() + " (Soin de: " + getEffect() + ")";
    }

}
