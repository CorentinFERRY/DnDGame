package fr.campus.dndgame.dao.interfaces;

import fr.campus.dndgame.model.enemies.Enemy;

import java.sql.SQLException;
import java.util.List;

public interface EnemyDao {
    public Enemy getEnemy(int id) throws SQLException;
    public List<Enemy> getAllEnemies () throws SQLException;
    public int add(Enemy enemy) throws SQLException;
    public void update(Enemy enemy) throws SQLException;
    public void delete(int id) throws SQLException;
    public void updateHealth(Enemy enemy) throws SQLException;
}
