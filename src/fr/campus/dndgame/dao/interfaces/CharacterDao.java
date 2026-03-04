package fr.campus.dndgame.dao.interfaces;

import java.sql.SQLException;

public interface CharacterDao {
    int add(Character character) throws SQLException;

}
