package fr.campus.dndgame.dao.impl;

import fr.campus.dndgame.board.Board;
import fr.campus.dndgame.dao.interfaces.BoardDao;
import fr.campus.dndgame.db.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BoardDaoImpl implements BoardDao {
    static Connection con = DatabaseConnection.getConnection();

    @Override
    public Board getBoard(int id) throws SQLException {
        String query = "SELECT * FROM boards WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        Board board = null;
        if(rs.next()){
            int size = rs.getInt("size");
            board = new Board(size);
            return board;
        }
        return null;
    }

    @Override
    public List<Board> getBoards() throws SQLException {
        return List.of();
    }

    @Override
    public int add(Board board) throws SQLException {
        return 0;
    }

    @Override
    public void delete(int id) throws SQLException {

    }

    @Override
    public void update(Board board) throws SQLException {

    }
}
