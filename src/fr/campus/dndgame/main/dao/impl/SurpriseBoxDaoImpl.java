package fr.campus.dndgame.main.dao.impl;

import fr.campus.dndgame.main.dao.interfaces.SurpriseBoxDao;
import fr.campus.dndgame.main.db.DatabaseConnection;
import fr.campus.dndgame.main.model.equipments.Equipment;
import fr.campus.dndgame.main.model.equipments.SurpriseBox;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation DAO pour la gestion des boîtes surprises.
 * Fournit les opérations CRUD pour les boîtes surprises en utilisant JDBC.
 * Gère les relations avec les équipements offensifs et défensifs.
 * 
 * @author CorentinFERRY
 * @version 1.0
 */
public class SurpriseBoxDaoImpl implements SurpriseBoxDao {

    static Connection con = DatabaseConnection.getConnection();

    /**
     * Récupère une boîte surprise par son identifiant depuis la base de données.
     * Charge aussi l'équipement associé (offensif ou défensif).
     * 
     * @param id L'identifiant de la boîte surprise
     * @return La boîte surprise correspondante, ou null si elle n'existe pas
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    @Override
    public SurpriseBox getSurpriseBox(int id) throws SQLException {
        String query = "SELECT * FROM surpriseBoxes WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            String name = rs.getString("name");
            // Récupération de l'ID offensif (nullable)(Interger)
            Integer offensiveId = rs.getObject("offensiveEquipment_id", Integer.class);
            Integer defensiveId = rs.getObject("defensiveEquipment_id", Integer.class);
            OffensiveEquipmentDaoImpl offensiveDAO = new OffensiveEquipmentDaoImpl();
            DefensiveEquipmentDaoImpl defensiveDAO = new DefensiveEquipmentDaoImpl();
            Equipment equipment = null;
            if (offensiveId != null) {
                equipment = offensiveDAO.getEquipment(offensiveId);
            } else if (defensiveId != null) {
                equipment = defensiveDAO.getEquipment(defensiveId);
            }
            if (equipment != null) {
                SurpriseBox box = new SurpriseBox(id, equipment);
                box.setName(name);
                return box;
            }
        }
        return null;
    }

    /**
     * Récupère toutes les boîtes surprises de la base de données.
     * Charge aussi les équipements associés (offensifs ou défensifs).
     * 
     * @return Une liste contenant toutes les boîtes surprises
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    @Override
    public List<SurpriseBox> getAllSurpriseBox() throws SQLException {
        String query = "SELECT * FROM surpriseBoxes";
        PreparedStatement stmt = con.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        List<SurpriseBox> list = new ArrayList<>();
        while (rs.next()) {
            String name = rs.getString("name");
            int id = rs.getInt("id");
            Integer offensiveId = rs.getObject("offensiveEquipment_id", Integer.class);
            Integer defensiveId = rs.getObject("defensiveEquipment_id", Integer.class);

            OffensiveEquipmentDaoImpl offensiveDAO = new OffensiveEquipmentDaoImpl();
            DefensiveEquipmentDaoImpl defensiveDAO = new DefensiveEquipmentDaoImpl();

            Equipment equipment = null;
            if (offensiveId != null) {
                equipment = offensiveDAO.getEquipment(offensiveId);
            } else if (defensiveId != null) {
                equipment = defensiveDAO.getEquipment(defensiveId);
            }
            if (equipment != null) {
                SurpriseBox box = new SurpriseBox(id, equipment);
                box.setName(name);
                list.add(box);
            }
        }
        return list;
    }

    /**
     * Ajoute une nouvelle boîte surprise à la base de données.
     * Génère un identifiant unique et l'affecte à la boîte passée en paramètre.
     * Gère l'association avec l'équipement (offensif ou défensif).
     * 
     * @param box La boîte surprise à ajouter
     * @return Le nombre de lignes affectées (1 si succès)
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    @Override
    public int add(SurpriseBox box) throws SQLException {
        String query = "INSERT INTO surpriseBoxes(name,offensiveEquipment_id,defensiveEquipment_id) VALUES (?,?,?)";
        PreparedStatement stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, box.getName());
        if (box.getEquipment().isOffensive()){
            stmt.setInt(2,box.getEquipment().getId());
            stmt.setNull(3,java.sql.Types.INTEGER);
        }
        else{
            stmt.setInt(2,java.sql.Types.INTEGER);
            stmt.setNull(3,box.getEquipment().getId());
        }
        int affectedRows = stmt.executeUpdate();
        if (affectedRows > 0) {
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                box.setId(id);
            }
        }
        return affectedRows;
    }

    /**
     * Met à jour une boîte surprise existante dans la base de données.
     * Actualise le nom et l'association avec l'équipement.
     * 
     * @param box La boîte surprise avec les nouvelles données
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    @Override
    public void update(SurpriseBox box) throws SQLException {
        String query = "UPDATE surpriseboxes SET name = ?, "
                + "offensiveEquipment_id = ?, "
                + "defensiveEquipment_id = ? WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1,box.getName());
        if (box.getEquipment().isOffensive()){
            stmt.setInt(2,box.getEquipment().getId());
            stmt.setNull(3,java.sql.Types.INTEGER);
        }
        else{
            stmt.setInt(2,java.sql.Types.INTEGER);
            stmt.setNull(3,box.getEquipment().getId());
        }
        stmt.executeUpdate();
    }

    /**
     * Supprime une boîte surprise de la base de données.
     * 
     * @param id L'identifiant de la boîte surprise à supprimer
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM surpriseBoxes WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setInt(1,id);
        stmt.executeUpdate();
    }
}
