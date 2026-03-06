package fr.campus.dndgame.dao.impl;

import fr.campus.dndgame.dao.interfaces.SurpriseBoxDao;
import fr.campus.dndgame.db.DatabaseConnection;
import fr.campus.dndgame.model.equipments.Equipment;
import fr.campus.dndgame.model.equipments.SurpriseBox;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SurpriseBoxDaoImpl implements SurpriseBoxDao {

    static Connection con = DatabaseConnection.getConnection();

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

    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM surpriseBoxes WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setInt(1,id);
        stmt.executeUpdate();
    }
}
