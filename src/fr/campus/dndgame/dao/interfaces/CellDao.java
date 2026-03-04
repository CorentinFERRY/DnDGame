package fr.campus.dndgame.dao.interfaces;

import fr.campus.dndgame.board.Cell;

import java.sql.SQLException;
import java.util.List;

public interface CellDao {
    public Cell getCell(int id) throws SQLException;
    public List<Cell> getCells() throws SQLException;
    public int add(Cell cell) throws SQLException;
    public void delete(int id) throws SQLException;
    public void update(Cell cell) throws SQLException;
}
