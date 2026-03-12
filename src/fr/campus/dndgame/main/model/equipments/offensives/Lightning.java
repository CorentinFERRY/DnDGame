package fr.campus.dndgame.main.model.equipments.offensives;

/**
 * Représente le sort offensif éclair.
 */
public class Lightning extends Spell{
    /**
     * Constructeur pour créer un éclair.
     */
    public Lightning() {
        super("Lightning", 2);
    }

    /**
     * Retourne une représentation textuelle de l'éclair.
     *
     * @return description du sort
     */
    @Override
    public String toString() {
        return "Spell : Éclair (Attaque: " + getEffect() + ")";
    }
}
