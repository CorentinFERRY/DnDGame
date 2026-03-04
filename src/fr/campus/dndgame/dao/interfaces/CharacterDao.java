package fr.campus.dndgame.dao.interfaces;

import fr.campus.dndgame.characters.Character;

import java.sql.SQLException;
import java.util.List;

public interface CharacterDao {

    public Character getCharacter(int id)
        throws SQLException;

    public List<Character> getCharacters()
        throws SQLException;

    public int add(Character character)
        throws SQLException;

    public void delete(int id)
        throws SQLException;

    public void update(Character character)
        throws SQLException;

    public void updateHealth(Character character)
        throws SQLException;
}
