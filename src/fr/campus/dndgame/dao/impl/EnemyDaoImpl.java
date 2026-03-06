package fr.campus.dndgame.dao.impl;

import fr.campus.dndgame.dao.interfaces.EnemyDao;
import fr.campus.dndgame.db.DatabaseConnection;
import fr.campus.dndgame.factory.EnemyFactory;
import fr.campus.dndgame.model.enemies.Enemy;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnemyDaoImpl implements EnemyDao {

    static Connection con = DatabaseConnection.getConnection();

    @Override
    public Enemy getEnemy(int id) throws SQLException {
        String query = "SELECT * FROM enemies WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            String name = rs.getString("name");
            int health = rs.getInt("health");
            int maxHealth = rs.getInt("maxHealth");
            int attack = rs.getInt("attack");
            int defense = rs.getInt("defense");
            return EnemyFactory.createFromDatabase(id, name, health, maxHealth, attack, defense);
        }
        return null;
    }

    @Override
    public List<Enemy> getAllEnemies() throws SQLException {
        String query = "SELECT * FROM enemies";
        PreparedStatement stmt = con.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        List<Enemy> list = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            int health = rs.getInt("health");
            int maxHealth = rs.getInt("maxHealth");
            int attack = rs.getInt("attack");
            int defense = rs.getInt("defense");
            Enemy enemy = EnemyFactory.createFromDatabase(id, name, health, maxHealth, attack, defense);
            list.add(enemy);
        }
        return list;
    }

    @Override
    public int add(Enemy enemy) throws SQLException {
        String query = "INSERT INTO enemies(name, health, maxHealth, attack,defense) VALUES (?, ?, ?, ?,?)";

        PreparedStatement stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, enemy.getName());
        stmt.setInt(2, enemy.getHealth());
        stmt.setInt(3, enemy.getMaxHealth());
        stmt.setInt(4, enemy.getAttack());
        stmt.setInt(5,enemy.getDefense());
        int affectedRows = stmt.executeUpdate();
        if (affectedRows > 0) {
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                enemy.setId(id);
            }
        }
        return affectedRows;
    }

    @Override
    public void update(Enemy enemy) throws SQLException {
        String query = "UPDATE enemies SET name = ?, "
                + "health = ?, "
                + "attack = ?, "
                + "defense = ? WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1, enemy.getName());
        stmt.setInt(2, enemy.getHealth());
        stmt.setInt(3, enemy.getAttack());
        stmt.setInt(4, enemy.getDefense());
        stmt.setInt(5, enemy.getId());
        stmt.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM enemies WHERE id = ? ";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setInt(1,id);
        stmt.executeUpdate();
    }

    @Override
    public void updateHealth(Enemy enemy) throws SQLException {
        String query = "UPDATE enemies SET health = ? WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setInt(1, enemy.getHealth());
        stmt.setInt(2, enemy.getId());
        stmt.executeUpdate();
    }
}
