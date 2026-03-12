package fr.campus.dndgame.main.model.equipments.offensives;

/**
 * Représente l'arme offensive massue.
 */
public class Mace extends Weapon{
    /**
     * Constructeur pour créer une massue.
     *
     */
    public Mace() {
        super("Mace", 3);
    }

    /**
     * Retourne une représentation textuelle de la massue.
     *
     * @return description de l'arme
     */
    @Override
    public String toString() {
        return "Weapon : Massue (Attaque: " + getEffect() + ")";
    }
}
