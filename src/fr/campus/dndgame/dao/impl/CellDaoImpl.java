package fr.campus.dndgame.dao.impl;

import fr.campus.dndgame.model.board.Cell;
import fr.campus.dndgame.dao.interfaces.CellDao;
import fr.campus.dndgame.db.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CellDaoImpl implements CellDao {
    static Connection con = DatabaseConnection.getConnection();

    @Override
    public Cell getCell(int id) throws SQLException {
        String query = "SELECT * FROM cells WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        Cell cell = null;
        if(rs.next()){
            int position = rs.getInt("position");
            cell = new Cell(id,position);
            return cell;
        }
        return null;
    }

    @Override
    public List<Cell> getCells() throws SQLException {
        String query = "SELECT * FROM cells";
        PreparedStatement stmt = con.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        List<Cell> list = new ArrayList<>();
        while(rs.next()){
            int position = rs.getInt("position");
            int id = rs.getInt("id");
            Cell cell = new Cell(id,position);
            list.add(cell);
        }
        return list;
    }

    @Override
    public int add(Cell cell) throws SQLException {
        String query = "INSERT INTO cell(position, board_id, character_id,ennemy_id,surpriseBox_id) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
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

    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM cells WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1,id);
        ps.executeUpdate();
    }

    @Override
    public void update(Cell cell) throws SQLException {
        String query = "UPDATE cells SET position = ?, "
                + "board_id = ?, "
                + "character_id = ?, "
                + "enemy_id = ?, "
                + "surpriseBox_id = ? WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setInt(1,cell.getNumber());
        stmt.setInt(2,cell.getBoardId());
        stmt.setInt(3,cell.getCharacter().getId());
        stmt.setInt(4,cell.getEnemy().getId());
        stmt.setInt(5,cell.getBox().getId());
        stmt.setInt(6,cell.getId());
        stmt.executeUpdate();
    }

    @Override
    public List<Cell> getCellsByBoardId(int board_id) throws SQLException{
        String query = "SELECT * FROM cells WHERE board_id = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setInt(1, board_id);
        ResultSet rs = stmt.executeQuery();
        List<Cell> list = new ArrayList<>();
        while(rs.next()){
            int position = rs.getInt("position");
            int id = rs.getInt("id");
            Cell cell = new Cell(id,position);
            list.add(cell);
        }
        return list;
    }
}
