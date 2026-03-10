package fr.campus.dndgame.main.dao.impl;

import fr.campus.dndgame.main.dao.interfaces.EquipmentDao;
import fr.campus.dndgame.main.db.DatabaseConnection;
import fr.campus.dndgame.main.factory.EquipmentFactory;
import fr.campus.dndgame.main.model.equipments.Equipment;

import java.sql.Connection;

import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation DAO pour les équipements offensifs.
 * Fournit les opérations CRUD pour les équipements offensifs en utilisant JDBC.
 * Utilise la factory pour créer les instances des équipements depuis la base de données.
 * 
 * @author CorentinFERRY
 * @version 1.0
 */
public class OffensiveEquipmentDaoImpl implements EquipmentDao {

    static Connection con = DatabaseConnection.getConnection();

    /**
     * Récupère un équipement offensif par son identifiant depuis la base de données.
     * 
     * @param id L'identifiant de l'équipement
     * @return L'équipement correspondant, ou null s'il n'existe pas
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    @Override
    public Equipment getEquipment(int id) throws SQLException {
        String query = "SELECT * FROM offensiveEquipments WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setInt(1,id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            String type = rs.getString("type");
            String name = rs.getString("name");
            int effect = rs.getInt("effect");
            return EquipmentFactory.createFromDatabase(id,type,name,effect);
        }
        return null;
    }

    /**
     * Récupère tous les équipements offensifs de la base de données.
     * 
     * @return Une liste contenant tous les équipements offensifs
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    @Override
    public List<Equipment> getEquipments() throws SQLException {
        String query = "SELECT * FROM offensiveEquipments";
        PreparedStatement stmt = con.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        List<Equipment> list = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String type = rs.getString("type");
            String name = rs.getString("name");
            int effect = rs.getInt("effect");
            Equipment equip = EquipmentFactory.createFromDatabase(id,type,name,effect);
            list.add(equip);
        }
        return list;
    }

    /**
     * Ajoute un nouvel équipement offensif à la base de données.
     * Génère un identifiant unique et l'affecte à l'équipement passé en paramètre.
     * 
     * @param equipment L'équipement à ajouter
     * @return Le nombre de lignes affectées (1 si succès)
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    @Override
    public int add(Equipment equipment) throws SQLException {
        String query = "INSERT INTO offensiveEquipments(name, type, effect) VALUES (?, ?, ?)";
        PreparedStatement stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, equipment.getName());
        stmt.setString(2, equipment.getType());
        stmt.setInt(3, equipment.getEffect());
        int affectedRows = stmt.executeUpdate();
        if (affectedRows > 0) {
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                equipment.setId(id);
            }
        }
        return affectedRows;
    }

    /**
     * Met à jour un équipement offensif existant dans la base de données.
     * Actualise tous les attributs (type, nom, effet).
     * 
     * @param equipment L'équipement avec les nouvelles données
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    @Override
    public void update(Equipment equipment) throws SQLException {
        String query = "UPDATE offensiveequipments SET type = ?, "
                + "name = ?, "
                + "effect = ? WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1, equipment.getType());
        stmt.setString(2, equipment.getName());
        stmt.setInt(3, equipment.getEffect());
        stmt.setInt(4,equipment.getId());
        stmt.executeUpdate();
    }

    /**
     * Supprime un équipement offensif de la base de données.
     * 
     * @param id L'identifiant de l'équipement à supprimer
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM offensiveEquipments WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, id);
        ps.executeUpdate();
    }
}
