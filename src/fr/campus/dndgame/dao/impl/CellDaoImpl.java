package fr.campus.dndgame.dao.impl;

import fr.campus.dndgame.board.Cell;
import fr.campus.dndgame.characters.Character;
import fr.campus.dndgame.dao.interfaces.CellDao;
import fr.campus.dndgame.db.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public abstract class CellDaoImpl implements CellDao {
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
            cell = new Cell(position);
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
            Cell cell = new Cell(position);
            list.add(cell);
        }
        return list;
    }
}
