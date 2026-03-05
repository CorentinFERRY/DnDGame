package fr.campus.dndgame.dao.impl;

import fr.campus.dndgame.dao.interfaces.SurpriseBoxDao;
import fr.campus.dndgame.db.DatabaseConnection;
import fr.campus.dndgame.model.equipments.Equipment;
import fr.campus.dndgame.model.equipments.OffensiveEquipment;
import fr.campus.dndgame.model.equipments.SurpriseBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SurpriseBoxDaoImpl implements SurpriseBoxDao {

    static Connection con = DatabaseConnection.getConnection();

    @Override
    public SurpriseBox getSurpriseBox(int id) throws SQLException {
        return null;
    }

    @Override
    public List<SurpriseBox> getAllSurpriseBox() throws SQLException {
        return List.of();
    }

    @Override
    public int add(SurpriseBox box) throws SQLException {
        return 0;
    }

    @Override
    public void update(SurpriseBox box) throws SQLException {

    }

    @Override
    public void delete(int id) throws SQLException {

    }
}
