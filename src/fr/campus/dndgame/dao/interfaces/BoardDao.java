package fr.campus.dndgame.dao.interfaces;

import fr.campus.dndgame.model.board.Board;

import java.sql.SQLException;
import java.util.List;

public interface BoardDao {

    public Board getBoard(int id)
            throws SQLException;

    public List<Board> getBoards()
            throws SQLException;

    public int add(Board board)
            throws SQLException;

    public void delete(int id)
            throws SQLException;

    public void update(Board board)
            throws SQLException;

    public List<Board> getBoardsWithCells()
            throws SQLException;
}