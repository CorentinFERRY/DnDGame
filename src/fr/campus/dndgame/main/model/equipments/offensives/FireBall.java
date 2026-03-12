package fr.campus.dndgame.main.model.equipments.offensives;

/**
 * Représente le sort offensif boule de feu.
 */
public class FireBall extends Spell{
    /**
     * Constructeur pour créer une boule de feu.
     *
     */
    public FireBall() {
        super("FireBall", 7);
    }

    /**
     * Retourne une représentation textuelle de la boule de feu.
     *
     * @return description du sort
     */
    @Override
    public String toString() {
        return "Spell : Boule de feu (Attaque: " + getEffect() + ")";
    }
}
