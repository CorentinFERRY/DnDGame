package fr.campus.dndgame.main.dao.interfaces;

import fr.campus.dndgame.main.model.enemies.Enemy;

import java.sql.SQLException;
import java.util.List;

/**
 * Contrat DAO pour la persistance des ennemis.
 */
public interface EnemyDao {
    /**
     * Récupère un ennemi à partir de son identifiant.
     *
     * @param id identifiant de l'ennemi
     * @return l'ennemi trouvé, ou {@code null}
     * @throws SQLException en cas d'erreur SQL
     */
    Enemy getEnemy(int id) throws SQLException;

    /**
     * Récupère l'ensemble des ennemis persistés.
     *
     * @return liste des ennemis
     * @throws SQLException en cas d'erreur SQL
     */
    List<Enemy> getAllEnemies () throws SQLException;

    /**
     * Ajoute un ennemi en base de données.
     *
     * @param enemy ennemi à ajouter
     * @return nombre de lignes affectées
     * @throws SQLException en cas d'erreur SQL
     */
    int add(Enemy enemy) throws SQLException;

    /**
     * Met à jour un ennemi en base de données.
     *
     * @param enemy ennemi à mettre à jour
     * @throws SQLException en cas d'erreur SQL
     */
    void update(Enemy enemy) throws SQLException;

    /**
     * Supprime un ennemi par identifiant.
     *
     * @param id identifiant de l'ennemi
     * @throws SQLException en cas d'erreur SQL
     */
    void delete(int id) throws SQLException;

    /**
     * Met à jour les points de vie courants d'un ennemi.
     *
     * @param enemy ennemi contenant la nouvelle valeur de santé
     * @throws SQLException en cas d'erreur SQL
     */
    void updateHealth(Enemy enemy) throws SQLException;
}
