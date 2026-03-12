package fr.campus.dndgame.main.model.equipments.defensives;

/**
 * Représente une potion de grande taille avec un soin élevé.
 */
public class LargePotion extends Potion{
    /**
     * Constructeur pour créer une grande potion.
     *
     */
    public LargePotion() {
        super("LargePotion", 5);
    }

    /**
     * Retourne une représentation textuelle de la grande potion.
     *
     * @return description de la potion
     */
    @Override
    public String toString() {
        return "Potion : Grande potion (Soin de: " + getEffect() + ")";
    }
}
