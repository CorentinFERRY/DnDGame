package fr.campus.dndgame.dao.interfaces;

import fr.campus.dndgame.model.board.Cell;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface de données pour les opérations CRUD sur les cases du plateau.
 * Définit les méthodes pour ajouter, récupérer, mettre à jour et supprimer des
 * cases
 * dans la base de données.
 * 
 * @author CorentinFERRY
 * @version 1.0
 */
public interface CellDao {
    /**
     * Récupère une case par son identifiant.
     * 
     * @param id L'identifiant de la case
     * @return La case correspondante, ou null si elle n'existe pas
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    public Cell getCell(int id) throws SQLException;

    /**
     * Récupère toutes les cases de la base de données.
     * 
     * @return Une liste contenant toutes les cases
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    public List<Cell> getCells() throws SQLException;

    /**
     * Ajoute une nouvelle case à la base de données.
     * 
     * @param cell La case à ajouter
     * @return L'identifiant généré de la case ajoutée
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    public int add(Cell cell) throws SQLException;

    /**
     * Supprime une case de la base de données.
     * 
     * @param id L'identifiant de la case à supprimer
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    public void delete(int id) throws SQLException;

    /**
     * Met à jour une case existante dans la base de données.
     * 
     * @param cell La case avec les nouvelles données
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    public void update(Cell cell) throws SQLException;

    /**
     * Récupère toutes les cases appartenant à un plateau spécifique.
     * 
     * @param board_id L'identifiant du plateau
     * @return Une liste contenant toutes les cases du plateau
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    public List<Cell> getCellsByBoardId(int board_id) throws SQLException;
}
