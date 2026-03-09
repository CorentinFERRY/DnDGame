package fr.campus.dndgame.main.dao.impl;

import fr.campus.dndgame.main.model.board.Board;
import fr.campus.dndgame.main.dao.interfaces.BoardDao;
import fr.campus.dndgame.main.db.DatabaseConnection;
import fr.campus.dndgame.main.model.board.Cell;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation de l'interface BoardDao pour la gestion des plateaux de jeu en
 * base de données.
 * Fournit les opérations CRUD pour les plateaux en utilisant JDBC.
 * 
 * @author CorentinFERRY
 * @version 1.0
 */
public class BoardDaoImpl implements BoardDao {
    static Connection con = DatabaseConnection.getConnection();

    /**
     * Récupère un plateau par son identifiant depuis la base de données.
     * 
     * @param id L'identifiant du plateau
     * @return Le plateau correspondant, ou null s'il n'existe pas
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    @Override
    public Board getBoard(int id) throws SQLException {
        String query = "SELECT * FROM boards WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        Board board;
        if (rs.next()) {
            int size = rs.getInt("size");
            board = new Board(size);
            return board;
        }
        return null;
    }

    /**
     * Récupère tous les plateaux de la base de données.
     * 
     * @return Une liste contenant tous les plateaux
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    @Override
    public List<Board> getBoards() throws SQLException {
        String query = "SELECT * FROM boards";
        PreparedStatement stmt = con.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        List<Board> list = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            int size = rs.getInt("size");
            Board board;
            board = new Board(id, size, name);
            list.add(board);
        }
        return list;

    }

    /**
     * Ajoute un nouveau plateau à la base de données.
     * Génère un identifiant unique pour le plateau et l'affecte à l'objet.
     * 
     * @param board Le plateau à ajouter
     * @return Le nombre de lignes affectées (1 si succès)
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    @Override
    public int add(Board board) throws SQLException {
        String query = "INSERT INTO boards(name,size) VALUES (?,?)";
        PreparedStatement stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, board.getName());
        stmt.setInt(2, board.getSize());
        int affectedRows = stmt.executeUpdate();
        // Récupération de l'id généré par la BDD
        if (affectedRows > 0) {
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                board.setId(id); // Injection de l'id dans character
            }
        }
        return affectedRows;
    }

    /**
     * Supprime un plateau de la base de données.
     * 
     * @param id L'identifiant du plateau à supprimer
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM boards WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    /**
     * Met à jour un plateau existant dans la base de données.
     * 
     * @param board Le plateau avec les nouvelles données
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    @Override
    public void update(Board board) throws SQLException {
        String query = "UPDATE boards SET name = ?, "
                + "size = ? WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1, board.getName());
        stmt.setInt(2, board.getSize());
        stmt.executeUpdate();
    }

    /**
     * Récupère tous les plateaux avec leurs cases associées.
     * Charge pour chaque plateau la liste complète de ses cases.
     * 
     * @return Une liste contenant tous les plateaux avec leurs cases
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    @Override
    public List<Board> getBoardsWithCells() throws SQLException {
        String query = "SELECT * FROM boards";
        PreparedStatement stmt = con.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        List<Board> list = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            int size = rs.getInt("size");
            CellDaoImpl cell = new CellDaoImpl();
            List<Cell> cells = cell.getCellsByBoardId(id);
            Board board;
            board = new Board(id, size, name);
            board.setCells(cells);
            list.add(board);
        }
        return list;

    }
}
