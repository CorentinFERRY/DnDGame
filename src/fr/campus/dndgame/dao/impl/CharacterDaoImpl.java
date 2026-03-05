package fr.campus.dndgame.dao.impl;

import fr.campus.dndgame.factory.CharacterFactory;
import fr.campus.dndgame.dao.interfaces.CharacterDao;

import fr.campus.dndgame.model.characters.Character;
import fr.campus.dndgame.db.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CharacterDaoImpl implements CharacterDao {

    static Connection con = DatabaseConnection.getConnection();

    @Override
    public Character getCharacter(int id) throws SQLException{
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
            Character hero = CharacterFactory.createFromDatabase(id,type,name,health,maxHealth,attack,defense,position);
            return hero;
        }
        return null;
    }

    @Override
    public List<Character> getCharacters() throws SQLException{
        String query = "SELECT * FROM characters";
        PreparedStatement stmt = con.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        List<Character> list = new ArrayList<>();

        while(rs.next()){
            int id = rs.getInt("id");
            String type = rs.getString("type");
            String name = rs.getString("name");
            int health = rs.getInt("health");
            int maxHealth = rs.getInt("maxHealth");
            int position = rs.getInt("position");
            int attack = rs.getInt("attack");
            int defense = rs.getInt("defense");
            Character hero = CharacterFactory.createFromDatabase(id,type,name,health,maxHealth,attack,defense,position);
            list.add(hero);
        }
        return list;
    }

    @Override
    public int add(Character character) throws SQLException {
        String query = "INSERT INTO characters(name, type, health, maxHealth, attack) VALUES (?, ?, ?, ?, ?)";
        // Utiliser RETURN_GENERATED_KEYS pour récupérer l'id auto-incrémenté
        PreparedStatement stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, character.getName());
        stmt.setString(2, character.getType());
        stmt.setInt(3, character.getHealth());
        stmt.setInt(4, character.getMaxHealth());
        stmt.setInt(5, character.getAttack());
        int affectedRows = stmt.executeUpdate();
        // Récupération de l'id généré par la BDD
        if (affectedRows > 0) {
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                character.setId(id);  // Injection de l'id dans character
            }
        }
        return affectedRows;
    }

    @Override
    public void delete(int id) throws SQLException{
        String query = "DELETE FROM characters WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1,id);
        ps.executeUpdate();
    }

    @Override
    public void update(Character character) throws SQLException{
        String query = "UPDATE characters SET name = ?, "
                        + "health = ?, "
                        + "attack = ?, "
                        + "defense = ?, "
                        + "position = ? WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1,character.getName());
        stmt.setInt(2,character.getHealth());
        stmt.setInt(3,character.getAttack());
        stmt.setInt(4,character.getDefense());
        stmt.setInt(5,character.getPosition());
        stmt.setInt(6,character.getId());
        stmt.executeUpdate();
    }

    @Override
    public void updateHealth(Character character) throws SQLException{
        String query = "UPDATE characters SET health = ? WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setInt(1,character.getHealth());
        stmt.setInt(2,character.getId());
        stmt.executeUpdate();
    }


}
