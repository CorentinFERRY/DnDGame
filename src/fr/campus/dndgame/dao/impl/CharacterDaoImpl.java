package fr.campus.dndgame.dao.impl;

import fr.campus.dndgame.dao.interfaces.CharacterDao;

import fr.campus.dndgame.characters.Character;
import fr.campus.dndgame.db.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class CharacterDaoImpl implements CharacterDao {
    public int add(Character character) throws SQLException {
        String sql = "INSERT INTO characters(name, type, health, maxHealth, attack) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, character.getName());
        ps.setString(2, character.getType());
        ps.setInt(3, character.getHealth());
        ps.setInt(4, character.getMaxHealth());
        ps.setInt(5, character.getAttack());
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) return rs.getInt(1);
        return 0;
    }


}
