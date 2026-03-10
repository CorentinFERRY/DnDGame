package fr.campus.dndgame.main.factory;


import fr.campus.dndgame.main.model.equipments.*;
import fr.campus.dndgame.main.model.equipments.defensives.LargePotion;
import fr.campus.dndgame.main.model.equipments.defensives.Shield;
import fr.campus.dndgame.main.model.equipments.defensives.StandardPotion;
import fr.campus.dndgame.main.model.equipments.offensives.*;

/**
 * Fabrique utilitaire pour créer des équipements.
 * Fournit des méthodes pour créer de nouveaux équipements ou les charger depuis la base de données.
 * Supporte les types d'équipement suivants : Weapon, Spell, Potion, Shield.
 * 
 * @author CorentinFERRY
 * @version 1.0
 */
public class EquipmentFactory {
    /**
     * Crée un nouvel équipement à partir de son type métier.
     *
     * @param type type d'équipement attendu
     * @param name nom de l'équipement
     * @param effect effet associé à l'équipement
     * @return instance d'équipement correspondante
     */
    public static Equipment createNewEquipment(String type,String name,int effect) {
        return switch (type) {
            case "Weapon" -> switch (name){
                case "Mace" -> new Mace();
                case "Sword" -> new Sword();
                default -> throw new IllegalArgumentException("Nom inconnu : " + name);
            };
            case "Spell" -> switch (name) {
                case "Lightling" -> new Lightning();
                case "FireBall" -> new FireBall();
                default -> throw new IllegalArgumentException("Nom inconnu : " + name);
            };
            case "Potion" -> switch (name) {
                case "LargePotion" -> new LargePotion();
                case "StandardPotion" -> new StandardPotion();
                default -> throw new IllegalArgumentException("Nom inconnu : " + name);
            };
            case "Shield" -> new Shield(name,effect);
            default -> throw new IllegalArgumentException("Type inconnu : " + type);
        };
    }

    /**
     * Crée un équipement issu de la persistance et hydrate ses attributs.
     *
     * @param id identifiant en base de données
     * @param type type d'équipement
     * @param name nom de l'équipement
     * @param effect effet associé à l'équipement
     * @return instance d'équipement hydratée
     */
    public static Equipment createFromDatabase(
            int id,
            String type,
            String name,
            int effect) {
        Equipment equip = switch (type) {
            case "Weapon" -> switch (name){
                case "Mace" -> new Mace();
                case "Sword" -> new Sword();
                default -> throw new IllegalArgumentException("Nom inconnu : " + name);
            };
            case "Spell" -> switch (name) {
                case "Lightling" -> new Lightning();
                case "FireBall" -> new FireBall();
                default -> throw new IllegalArgumentException("Nom inconnu : " + name);
            };
            case "Potion" -> switch (name) {
                case "LargePotion" -> new LargePotion();
                case "StandardPotion" -> new StandardPotion();
                default -> throw new IllegalArgumentException("Nom inconnu : " + name);
            };
            case "Shield" -> new Shield(name,effect);
            default -> throw new IllegalArgumentException("Type inconnu : " + type);
        };
        // Injection des données BDD
        equip.setId(id);
        equip.setType(type);
        equip.setName(name);
        equip.setEffect(effect);
        return equip;
    }
}

