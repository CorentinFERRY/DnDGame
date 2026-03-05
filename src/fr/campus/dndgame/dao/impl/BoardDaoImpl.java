package fr.campus.dndgame.dao.impl;

import fr.campus.dndgame.model.board.Board;
import fr.campus.dndgame.dao.interfaces.BoardDao;
import fr.campus.dndgame.db.DatabaseConnection;
import fr.campus.dndgame.model.board.Cell;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class BoardDaoImpl implements BoardDao {
    static Connection con = DatabaseConnection.getConnection();

    @Override
    public Board getBoard(int id) throws SQLException {
        String query = "SELECT * FROM boards WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        Board board;
        if(rs.next()){
            int size = rs.getInt("size");
            board = new Board(size);
            return board;
        }
        return null;
    }

    @Override
    public List<Board> getBoards() throws SQLException {
        String query = "SELECT * FROM boards";
        PreparedStatement stmt = con.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        List<Board> list = new ArrayList<>();
        while(rs.next()){
            int id = rs.getInt("id");
            String name = rs.getString("name");
            int size = rs.getInt("size");
            Board board;
            board = new Board(id,size,name);
            list.add(board);
        }
        return list;

    }

    @Override
    public int add(Board board) throws SQLException {
        String query = "INSERT INTO boards(name,size) VALUES (?,?)";
        PreparedStatement stmt = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, board.getName());
        stmt.setInt(2,board.getSize());
        int affectedRows = stmt.executeUpdate();
        // Récupération de l'id généré par la BDD
        if (affectedRows > 0) {
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                board.setId(id);  // Injection de l'id dans character
            }
        }
        return affectedRows;
    }

    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM boards WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1,id);
        ps.executeUpdate();
    }

    @Override
    public void update(Board board) throws SQLException {
        String query = "UPDATE boards SET name = ?, "
                + "size = ? WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1,board.getName());
        stmt.setInt(2,board.getSize());
        stmt.executeUpdate();
    }

    @Override
    public List<Board> getBoardsWithCells() throws SQLException {
        String query = "SELECT * FROM boards";
        PreparedStatement stmt = con.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        List<Board> list = new ArrayList<>();

        while(rs.next()){
            int id = rs.getInt("id");
            String name = rs.getString("name");
            int size = rs.getInt("size");
            CellDaoImpl cell = new CellDaoImpl();
            List<Cell> cells = cell.getCellsByBoardId(id);
            Board board;
            board = new Board(id,size,name);
            board.setCells(cells);
            list.add(board);
        }
        return list;

    }
}
