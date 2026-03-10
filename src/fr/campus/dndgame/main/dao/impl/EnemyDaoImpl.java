package fr.campus.dndgame.main.dao.impl;

import fr.campus.dndgame.main.dao.interfaces.EnemyDao;
import fr.campus.dndgame.main.db.DatabaseConnection;
import fr.campus.dndgame.main.factory.EnemyFactory;
import fr.campus.dndgame.main.model.enemies.Enemy;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation DAO pour la gestion des ennemis.
 * Fournit les opérations CRUD pour les ennemis en utilisant JDBC.
 * Utilise le DatabaseConnection pour obtenir la connexion à la base de données.
 * 
 * @author CorentinFERRY
 * @version 1.0
 */
public class EnemyDaoImpl implements EnemyDao {

    static Connection con = DatabaseConnection.getConnection();

    /**
     * Récupère un ennemi par son identifiant depuis la base de données.
     * 
     * @param id L'identifiant de l'ennemi
     * @return L'ennemi correspondant, ou null s'il n'existe pas
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
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

    /**
     * Récupère tous les ennemis de la base de données.
     * 
     * @return Une liste contenant tous les ennemis
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
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

    /**
     * Ajoute un nouvel ennemi à la base de données.
     * Génère un identifiant unique et l'affecte à l'ennemi passé en paramètre.
     * 
     * @param enemy L'ennemi à ajouter
     * @return Le nombre de lignes affectées (1 si succès)
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
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

    /**
     * Met à jour un ennemi existant dans la base de données.
     * Actualise tous les attributs de l'ennemi (nom, attaque, défense).
     * 
     * @param enemy L'ennemi avec les nouvelles données
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
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

    /**
     * Supprime un ennemi de la base de données.
     * 
     * @param id L'identifiant de l'ennemi à supprimer
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM enemies WHERE id = ? ";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setInt(1,id);
        stmt.executeUpdate();
    }

    /**
     * Met à jour uniquement les points de vie d'un ennemi.
     * Utilisé lors des combats pour mettre à jour la santé de l'ennemi.
     * 
     * @param enemy L'ennemi dont la santé doit être mise à jour
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    @Override
    public void updateHealth(Enemy enemy) throws SQLException {
        String query = "UPDATE enemies SET health = ? WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setInt(1, enemy.getHealth());
        stmt.setInt(2, enemy.getId());
        stmt.executeUpdate();
    }
}
