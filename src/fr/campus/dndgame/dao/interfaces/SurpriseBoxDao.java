package fr.campus.dndgame.dao.interfaces;

import fr.campus.dndgame.model.equipments.SurpriseBox;

import java.sql.SQLException;
import java.util.List;

public interface SurpriseBoxDao {

    public SurpriseBox getSurpriseBox(int id) throws SQLException;

    public List<SurpriseBox> getAllSurpriseBox() throws SQLException;

    public int add(SurpriseBox box) throws SQLException;

    public void update (SurpriseBox box) throws SQLException;

    public void delete (int id) throws SQLException;
}
