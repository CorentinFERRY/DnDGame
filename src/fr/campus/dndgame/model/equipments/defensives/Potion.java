package fr.campus.dndgame.model.equipments.defensives;

import fr.campus.dndgame.model.characters.Character;

/**
 * Classe représentant une potion, un équipement défensif.
 * Une potion restaure les points de vie du personnage qui l'utilise.
 * 
 * @author CorentinFERRY
 * @version 1.0
 */
public class Potion extends DefensiveEquipment {

    private int healAmount;

    /**
     * Constructeur pour créer une potion.
     *
     * @param name Le nom de la potion
     * @param healAmount Le nombre de points de vie restaurés par la potion
     */
    public Potion(String name, int healAmount) {
        super("Potion", name);
        this.healAmount = healAmount;
    }

    // ========== GETTERS & SETTERS ==========

    /**
     * Définit l'effet de la potion.
     *
     * @param effect points de soin à appliquer
     */
    @Override
    public void setEffect(int effect) {
        this.healAmount = effect;
    }

    /**
     * Retourne l'effet de la potion.
     *
     * @return points de soin
     */
    @Override
    public int getEffect() {
        return healAmount;
    }

    /**
     * Retourne la quantité de points de vie restaurés par la potion.
     *
     * @return Le montant de guérison
     */
    public int getHealAmount(){
        return healAmount;
    }
    
    /**
     * Définit la quantité de points de vie restaurés par la potion.
     *
     * @param healAmount Nouveau montant de guérison
     */
    public void setHealAmount(int healAmount){
        this.healAmount =healAmount;
    }

    /**
     * Utilise la potion pour guérir le personnage.
     * Affiche un message indiquant les PV restaurés et l'état actuel.
     *
     * @param character Le personnage qui utilise la potion
     */
    @Override
    public void use(Character character){
        int newHealth = character.getHealth() + healAmount;
        if (newHealth > character.getMaxHealth()) {
            newHealth = character.getMaxHealth();
        }
        character.setHealth(newHealth);
        System.out.println(character.getName() + " utilise " + getName() + " et récupère " + healAmount + " PV ! " +
                "PV actuels : " + character.getHealth());
    }

    /**
     * Retourne une représentation textuelle de la potion.
     *
     * @return Une chaîne décrivant le montant de soin
     */
    @Override
    public String toString(){
        return super.toString() + " (Soin de: " + healAmount+")";
    }

}
