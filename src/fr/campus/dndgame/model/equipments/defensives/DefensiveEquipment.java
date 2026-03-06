package fr.campus.dndgame.model.equipments.defensives;

import fr.campus.dndgame.model.characters.Character;
import fr.campus.dndgame.model.equipments.Equipment;

/**
 * Classe abstraite représentant un équipement défensif.
 * Un équipement défensif peut être utilisé pour protéger ou soigner un personnage.
 * 
 * @author CorentinFERRY
 * @version 1.0
 */
public abstract class DefensiveEquipment extends Equipment {

    private int id;
    /**
     * Constructeur protégé pour initialiser un équipement défensif.
     *
     * @param type Le type d'équipement
     * @param name Le nom de l'équipement
     */
    protected DefensiveEquipment(String type,String name){
        super(type,name);
    }

    /**
     * Retourne l'identifiant de persistance de l'équipement défensif.
     *
     * @return identifiant en base de données
     */
    public int getId() {
        return id;
    }

    /**
     * Définit l'identifiant de persistance de l'équipement défensif.
     *
     * @param id identifiant en base de données
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Indique que cet équipement n'est pas offensif.
     *
     * @return toujours {@code false}
     */
    @Override
    public boolean isOffensive() {
        return false;
    }

    /**
     * Utilise l'équipement défensif sur un personnage.
     * Cette méthode doit être implémentée par les sous-classes.
     *
     * @param character Le personnage qui utilise l'équipement
     */
    public abstract void use(Character character);

}
