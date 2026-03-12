package fr.campus.dndgame.main.model.equipments.offensives;

/**
 * Représente l'arme offensive épée.
 */
public class Sword extends Weapon{
    /**
     * Constructeur pour créer une épée.
     *
     */
    public Sword() {
        super("Sword", 5);
    }

    /**
     * Retourne une représentation textuelle de l'épée.
     *
     * @return description de l'arme
     */
    @Override
    public String toString() {
        return "Weapon : Épée (Attaque: " + getEffect() + ")";
    }
}
