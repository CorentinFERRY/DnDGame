package fr.campus.dndgame.dao.impl;

import fr.campus.dndgame.characters.Warrior;
import fr.campus.dndgame.characters.Wizard;
import fr.campus.dndgame.dao.interfaces.CharacterDao;

import fr.campus.dndgame.characters.Character;
import fr.campus.dndgame.db.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class CharacterDaoImpl implements CharacterDao {

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
            int position = rs.getInt("position");
            int attack = rs.getInt("attack");
            int defense = rs.getInt("defense");
            Character hero = null;
            switch (type) {

                case "Warrior":
                    hero = new Warrior(name);
                    break;

                case "Wizard":
                    hero = new Wizard(name);
                    break;
            }
            if (hero != null) {
                hero.setHealth(health);
                hero.setPosition(position);
                hero.setAttack(attack);
                hero.setDefense(defense);
            }
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
            String type = rs.getString("type");
            String name = rs.getString("name");
            int health = rs.getInt("health");
            int position = rs.getInt("position");
            int attack = rs.getInt("attack");
            int defense = rs.getInt("defense");
            Character hero = null;
            switch (type) {

                case "Warrior":
                    hero = new Warrior(name);
                    break;

                case "Wizard":
                    hero = new Wizard(name);
                    break;
            }
            if (hero != null) {
                hero.setHealth(health);
                hero.setPosition(position);
                hero.setAttack(attack);
                hero.setDefense(defense);
            }
            list.add(hero);
        }
        return list;
    }

    @Override
    public int add(Character character) throws SQLException {
        String query = "INSERT INTO characters(name, type, health, maxHealth, attack) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = con.prepareStatement(query);

        stmt.setString(1, character.getName());
        stmt.setString(2, character.getType());
        stmt.setInt(3, character.getHealth());
        stmt.setInt(4, character.getMaxHealth());
        stmt.setInt(5, character.getAttack());
        return stmt.executeUpdate();
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
                        + "position = ?, ";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1,character.getName());
        stmt.setInt(2,character.getHealth());
        stmt.setInt(3,character.getAttack());
        stmt.setInt(4,character.getDefense());
        stmt.setInt(5,character.getPosition());

        stmt.executeUpdate();
    }

    @Override
    public void updateHealth(Character character) throws SQLException{
        String query = "UPDATE characters SET health = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setInt(1,character.getHealth());
        stmt.executeUpdate();
    }


}
