package fr.campus.dndgame.main.dao.impl;

import fr.campus.dndgame.main.model.board.Cell;
import fr.campus.dndgame.main.dao.interfaces.CellDao;
import fr.campus.dndgame.main.db.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation de l'interface CellDao pour la gestion des cases du plateau en
 * base de données.
 * Fournit les opérations CRUD pour les cases en utilisant JDBC.
 * 
 * @author CorentinFERRY
 * @version 1.0
 */
public class CellDaoImpl implements CellDao {
    static Connection con = DatabaseConnection.getConnection();

    /**
     * Récupère une case par son identifiant depuis la base de données.
     * 
     * @param id L'identifiant de la case
     * @return La case correspondante, ou null si elle n'existe pas
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    @Override
    public Cell getCell(int id) throws SQLException {
        String query = "SELECT * FROM cells WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        Cell cell = null;
        if (rs.next()) {
            int position = rs.getInt("position");
            cell = new Cell(id, position);
            return cell;
        }
        return null;
    }

    /**
     * Récupère toutes les cases de la base de données.
     * 
     * @return Une liste contenant toutes les cases
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    @Override
    public List<Cell> getCells() throws SQLException {
        String query = "SELECT * FROM cells";
        PreparedStatement stmt = con.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        List<Cell> list = new ArrayList<>();
        while (rs.next()) {
            int position = rs.getInt("position");
            int id = rs.getInt("id");
            Cell cell = new Cell(id, position);
            list.add(cell);
        }
        return list;
    }

    /**
     * Ajoute une nouvelle case à la base de données.
     * Génère un identifiant unique pour la case et l'affecte à l'objet.
     * Gère aussi les relations avec les personnages, ennemis et boîtes surprises.
     * 
     * @param cell La case à ajouter
     * @return Le nombre de lignes affectées (1 si succès)
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    @Override
    public int add(Cell cell) throws SQLException {
        String query = "INSERT INTO cells(position, board_id, character_id,enemy_id,surpriseBox_id) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, cell.getNumber());
        stmt.setInt(2, cell.getBoardId());
        if (!cell.isEmpty()) {
            if (cell.getCharacter() != null) {
                stmt.setInt(3, cell.getCharacter().getId());
            } else {
                stmt.setNull(3, java.sql.Types.INTEGER);
            }
            if (cell.getEnemy() != null) {
                stmt.setInt(4, cell.getEnemy().getId());
            } else {
                stmt.setNull(4, java.sql.Types.INTEGER);
            }
            if (cell.getBox() != null) {
                stmt.setInt(5, cell.getBox().getId());
            } else {
                stmt.setNull(5, java.sql.Types.INTEGER);
            }
        }
        int affectedRows = stmt.executeUpdate();
        // récupérer l'id généré pour la cellule
        if (affectedRows > 0) {
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                cell.setId(generatedKeys.getInt(1));
            }
        }
        return affectedRows;
    }

    /**
     * Supprime une case de la base de données.
     * 
     * @param id L'identifiant de la case à supprimer
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM cells WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    /**
     * Met à jour une case existante dans la base de données.
     * Actualise tous les attributs incluant les relations avec les personnages,
     * ennemis et boîtes surprises.
     * 
     * @param cell La case avec les nouvelles données
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    @Override
    public void update(Cell cell) throws SQLException {
        String query = "UPDATE cells SET position = ?, "
                + "board_id = ?, "
                + "character_id = ?, "
                + "enemy_id = ?, "
                + "surpriseBox_id = ? WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setInt(1, cell.getNumber());
        stmt.setInt(2, cell.getBoardId());
        if (cell.getCharacter() != null) {
            stmt.setInt(3, cell.getCharacter().getId());
        } else {
            stmt.setNull(3, java.sql.Types.INTEGER);
        }
        if (cell.getEnemy() != null) {
            stmt.setInt(4, cell.getEnemy().getId());
        } else {
            stmt.setNull(4, java.sql.Types.INTEGER);
        }
        if (cell.getBox() != null) {
            stmt.setInt(5, cell.getBox().getId());
        } else {
            stmt.setNull(5, java.sql.Types.INTEGER);
        }
        stmt.setInt(6, cell.getId());
        stmt.executeUpdate();
    }

    /**
     * Récupère toutes les cases appartenant à un plateau spécifique.
     * 
     * @param board_id L'identifiant du plateau
     * @return Une liste contenant toutes les cases du plateau
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    @Override
    public List<Cell> getCellsByBoardId(int board_id) throws SQLException {
        EnemyDaoImpl enemyDao = new EnemyDaoImpl();
        SurpriseBoxDaoImpl surpriseBoxDao = new SurpriseBoxDaoImpl();
        String query = "SELECT * FROM cells WHERE board_id = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setInt(1, board_id);
        ResultSet rs = stmt.executeQuery();
        List<Cell> list = new ArrayList<>();
        while (rs.next()) {
            int position = rs.getInt("position");
            int id = rs.getInt("id");
            Cell cell = new Cell(id, position);
            // Hydrater l'ennemi si présent
            Integer enemyId = rs.getObject("enemy_id", Integer.class);
            if (enemyId != null) {
                cell.setEnemy(enemyDao.getEnemy(enemyId));
            }
            // Hydrater la boîte surprise si présente
            Integer boxId = rs.getObject("surpriseBox_id", Integer.class);
            if (boxId != null) {
                cell.setBox(surpriseBoxDao.getSurpriseBox(boxId));
            }
            list.add(cell);
        }
        return list;
    }
}
