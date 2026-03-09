package fr.campus.dndgame.main.dao.interfaces;

import fr.campus.dndgame.main.model.board.Board;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface de données pour les opérations CRUD sur les plateaux de jeu.
 * Définit les méthodes pour ajouter, récupérer, mettre à jour et supprimer des
 * plateaux
 * dans la base de données.
 * 
 * @author CorentinFERRY
 * @version 1.0
 */
public interface BoardDao {

        /**
         * Récupère un plateau par son identifiant.
         * 
         * @param id L'identifiant du plateau
         * @return Le plateau correspondant, ou null s'il n'existe pas
         * @throws SQLException en cas d'erreur lors de l'accès à la base de données
         */
        Board getBoard(int id)
                        throws SQLException;

        /**
         * Récupère tous les plateaux de la base de données.
         * 
         * @return Une liste contenant tous les plateaux
         * @throws SQLException en cas d'erreur lors de l'accès à la base de données
         */
        List<Board> getBoards()
                        throws SQLException;

        /**
         * Ajoute un nouveau plateau à la base de données.
         * 
         * @param board Le plateau à ajouter
         * @return L'identifiant généré du plateau ajouté
         * @throws SQLException en cas d'erreur lors de l'accès à la base de données
         */
        int add(Board board)
                        throws SQLException;

        /**
         * Supprime un plateau de la base de données.
         * 
         * @param id L'identifiant du plateau à supprimer
         * @throws SQLException en cas d'erreur lors de l'accès à la base de données
         */
        void delete(int id)
                        throws SQLException;

        /**
         * Met à jour un plateau existant dans la base de données.
         * 
         * @param board Le plateau avec les nouvelles données
         * @throws SQLException en cas d'erreur lors de l'accès à la base de données
         */
        void update(Board board)
                        throws SQLException;

        /**
         * Récupère tous les plateaux avec leurs cases associées.
         * 
         * @return Une liste contenant tous les plateaux avec leurs cases
         * @throws SQLException en cas d'erreur lors de l'accès à la base de données
         */
        List<Board> getBoardsWithCells()
                        throws SQLException;
}