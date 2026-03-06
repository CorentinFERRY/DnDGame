package fr.campus.dndgame.dao.interfaces;

import fr.campus.dndgame.model.equipments.Equipment;

import java.sql.SQLException;
import java.util.List;

public interface EquipmentDao {
    public Equipment getEquipment(int id) throws SQLException;
    public List<Equipment> getEquipments() throws SQLException;
    public int add (Equipment equipment) throws SQLException;
    public void update(Equipment equipment) throws SQLException;
    public void delete(int id) throws SQLException;
}
