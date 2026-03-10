package fr.campus.dndgame.main.dao.impl;

import fr.campus.dndgame.main.factory.CharacterFactory;
import fr.campus.dndgame.main.dao.interfaces.CharacterDao;

import fr.campus.dndgame.main.model.characters.Character;
import fr.campus.dndgame.main.db.DatabaseConnection;
import fr.campus.dndgame.main.model.characters.Warrior;
import fr.campus.dndgame.main.model.characters.Wizard;
import fr.campus.dndgame.main.model.equipments.Equipment;
import fr.campus.dndgame.main.model.equipments.defensives.DefensiveEquipment;
import fr.campus.dndgame.main.model.equipments.offensives.Spell;
import fr.campus.dndgame.main.model.equipments.offensives.Weapon;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation de l'interface CharacterDao pour la gestion des personnages en
 * base de données.
 * Fournit les opérations CRUD pour les personnages en utilisant JDBC.
 * 
 * @author CorentinFERRY
 * @version 1.0
 */
public class CharacterDaoImpl implements CharacterDao {

    static Connection con = DatabaseConnection.getConnection();

    /**
     * Récupère un personnage par son identifiant depuis la base de données.
     * 
     * @param id L'identifiant du personnage
     * @return Le personnage correspondant, ou null s'il n'existe pas
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    @Override
    public Character getCharacter(int id) throws SQLException {
        String query = "SELECT * FROM characters WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            String type = rs.getString("type");
            String name = rs.getString("name");
            int health = rs.getInt("health");
            int maxHealth = rs.getInt("maxHealth");
            int position = rs.getInt("position");
            int attack = rs.getInt("attack");
            int defense = rs.getInt("defense");
            int boardId = rs.getInt("board_id");
            return CharacterFactory.createFromDatabase(id, type, name, health, maxHealth, attack, defense,
                    position,boardId);
        }
        return null;
    }

    /**
     * Récupère tous les personnages de la base de données.
     * 
     * @return Une liste contenant tous les personnages
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    @Override
    public List<Character> getCharacters() throws SQLException {
        String query = "SELECT * FROM characters";
        PreparedStatement stmt = con.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        List<Character> list = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt("id");
            String type = rs.getString("type");
            String name = rs.getString("name");
            int health = rs.getInt("health");
            int maxHealth = rs.getInt("maxHealth");
            int position = rs.getInt("position");
            int attack = rs.getInt("attack");
            int defense = rs.getInt("defense");
            int boardId = rs.getInt("board_id");
            Character hero = CharacterFactory.createFromDatabase(id, type, name, health, maxHealth, attack, defense,
                    position,boardId);
            list.add(hero);
        }
        return list;
    }

    /**
     * Ajoute un nouveau personnage à la base de données.
     * Génère un identifiant unique et l'affecte au personnage passé en paramètre.
     * 
     * @param character Le personnage à ajouter
     * @return Le nombre de lignes affectées (1 si succès)
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    @Override
    public int add(Character character) throws SQLException {
        String query = "INSERT INTO characters(name, type, health, maxHealth, attack,defense,position,board_id) VALUES (?, ?, ?, ?, ?,?,?,?)";
        // Utiliser RETURN_GENERATED_KEYS pour récupérer l'id auto-incrémenté
        PreparedStatement stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, character.getName());
        stmt.setString(2, character.getType());
        stmt.setInt(3, character.getHealth());
        stmt.setInt(4, character.getMaxHealth());
        stmt.setInt(5, character.getAttack());
        stmt.setInt(6,character.getDefense());
        stmt.setInt(7,character.getPosition());
        stmt.setInt(8,character.getBoardId());
        int affectedRows = stmt.executeUpdate();
        // Récupération de l'id généré par la BDD
        if (affectedRows > 0) {
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                character.setId(id); // Injection de l'id dans character
            }
        }
        return affectedRows;
    }

    /**
     * Supprime un personnage de la base de données.
     * 
     * @param id L'identifiant du personnage à supprimer
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM characters WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    /**
     * Met à jour un personnage existant dans la base de données.
     * Actualise tous les attributs du personnage (nom, santé, attaque, défense, position).
     * 
     * @param character Le personnage avec les nouvelles données
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    @Override
    public void update(Character character) throws SQLException {
        String query = "UPDATE characters SET name = ?, "
                + "health = ?, "
                + "attack = ?, "
                + "defense = ?, "
                + "position = ?, "
                + "board_id = ? WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1, character.getName());
        stmt.setInt(2, character.getHealth());
        stmt.setInt(3, character.getAttack());
        stmt.setInt(4, character.getDefense());
        stmt.setInt(5, character.getPosition());
        stmt.setInt(6,character.getBoardId());
        stmt.setInt(7, character.getId());
        stmt.executeUpdate();
    }

    /**
     * Met à jour uniquement les points de vie d'un personnage.
     * Utilisé lors des combats pour mettre à jour la santé du personnage.
     * 
     * @param character Le personnage dont la santé doit être mise à jour
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    @Override
    public void updateHealth(Character character) throws SQLException {
        String query = "UPDATE characters SET health = ? WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setInt(1, character.getHealth());
        stmt.setInt(2, character.getId());
        stmt.executeUpdate();
    }
    @Override
    public Character getCharacterWithEquipment(int id) throws SQLException {
        // On réutilise getCharacter() pour ne pas dupliquer le code
        Character character = getCharacter(id);
        if (character == null) return null;

        // Récupérer les FK d'équipements
        String query = "SELECT offensiveEquipment_id, defensiveEquipment_id FROM characters WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            OffensiveEquipmentDaoImpl offensiveDao = new OffensiveEquipmentDaoImpl();
            DefensiveEquipmentDaoImpl defensiveDao = new DefensiveEquipmentDaoImpl();

            Integer offensiveId = rs.getObject("offensive_equipment_id", Integer.class);
            Integer defensiveId = rs.getObject("defensive_equipment_id", Integer.class);

            if (offensiveId != null) {
                Equipment equip = offensiveDao.getEquipment(offensiveId);
                if (equip instanceof Weapon weapon && character instanceof Warrior warrior)
                    warrior.setWeapon(weapon);
                else if (equip instanceof Spell spell && character instanceof Wizard wizard)
                    wizard.setSpell(spell);
            }

            if (defensiveId != null) {
                Equipment equip = defensiveDao.getEquipment(defensiveId);
                if (equip instanceof DefensiveEquipment def)
                    character.setDefensiveEquipment(def);
            }
        }
        return character;
    }

}
