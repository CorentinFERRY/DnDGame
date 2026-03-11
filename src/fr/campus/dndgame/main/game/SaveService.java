package fr.campus.dndgame.main.game;

import fr.campus.dndgame.main.dao.impl.*;
import fr.campus.dndgame.main.model.board.Board;
import fr.campus.dndgame.main.model.board.Cell;
import fr.campus.dndgame.main.model.characters.Character;
import fr.campus.dndgame.main.model.characters.Warrior;
import fr.campus.dndgame.main.model.characters.Wizard;
import fr.campus.dndgame.main.model.enemies.Enemy;
import fr.campus.dndgame.main.model.equipments.Equipment;
import fr.campus.dndgame.main.model.equipments.SurpriseBox;
import fr.campus.dndgame.main.model.equipments.defensives.DefensiveEquipment;


import java.sql.SQLException;
import java.util.List;


public class SaveService {
    private final CharacterDaoImpl characterDao = new CharacterDaoImpl();
    private final BoardDaoImpl boardDao = new BoardDaoImpl();
    private final CellDaoImpl cellDao = new CellDaoImpl();
    private final EnemyDaoImpl enemyDao = new EnemyDaoImpl();
    private final OffensiveEquipmentDaoImpl offensiveEquipmentDao = new OffensiveEquipmentDaoImpl();
    private final DefensiveEquipmentDaoImpl defensiveEquipmentDao = new DefensiveEquipmentDaoImpl();
    private final SurpriseBoxDaoImpl surpriseBoxDao = new SurpriseBoxDaoImpl();

    public void saveGame(Character player, Board board) throws SQLException {
        // Ordre important : les dépendances d'abord
        saveOffensiveEquipment(player);   // 1. équipement offensif du joueur
        saveDefensiveEquipment(player);   // 2. équipement défensif du joueur
        saveBoard(board);                 // 3. le plateau

        for (Cell cell : board.getCells()) {
            saveCellContents(cell);       // 4. ennemis et boîtes de chaque case
            cell.setBoardId(board.getId());
            saveCell(cell);              // 5. la case elle-même
        }

        player.setBoardId(board.getId()); // 6. lier le joueur au plateau
        saveCharacter(player);            // 7. le joueur en dernier
    }

    // Pattern "save or update" : add si id = 0, update sinon (existant)
    private void saveCharacter(Character player) throws SQLException {
        if (player.getId() > 0) characterDao.update(player);
        else characterDao.add(player);
    }

    private void saveBoard(Board board) throws SQLException {
        if (board.getId() > 0) boardDao.update(board);
        else boardDao.add(board);
    }

    private void saveCell(Cell cell) throws SQLException {
        if (cell.getId() > 0) cellDao.update(cell);
        else cellDao.add(cell);
    }

    private void saveOffensiveEquipment(Character player) throws SQLException {
        Equipment equip = null;
        if (player instanceof Warrior warrior && warrior.getWeapon() != null)
            equip = warrior.getWeapon();
        else if (player instanceof Wizard wizard && wizard.getSpell() != null)
            equip = wizard.getSpell();

        if (equip != null) {
            if (equip.getId() > 0) offensiveEquipmentDao.update(equip);
            else offensiveEquipmentDao.add(equip);
        }
    }

    private void saveDefensiveEquipment(Character player) throws SQLException {
        DefensiveEquipment equip = player.getDefensiveEquipment();
        if (equip != null) {
            if (equip.getId() > 0) defensiveEquipmentDao.update(equip);
            else defensiveEquipmentDao.add(equip);
        }
    }

    private void saveCellContents(Cell cell) throws SQLException {
        // Sauvegarder l'ennemi si présent
        if (cell.getEnemy() != null) {
            Enemy enemy = cell.getEnemy();
            if (enemy.getId() > 0) enemyDao.update(enemy);
            else enemyDao.add(enemy);
        }
        // Sauvegarder la boîte et son équipement si présente
        if (cell.getBox() != null) {
            SurpriseBox box = cell.getBox();
            Equipment equip = box.getEquipment();
            if (equip.isOffensive()) {
                if (equip.getId() > 0) offensiveEquipmentDao.update(equip);
                else offensiveEquipmentDao.add(equip);
            }
            else {
                if (equip.getId() > 0) defensiveEquipmentDao.update(equip);
                else defensiveEquipmentDao.add(equip);
            }
            if (box.getId() > 0) surpriseBoxDao.update(box);
            else surpriseBoxDao.add(box);
        }
    }

    // Retourne toutes les sauvegardes pour le menu
    public List<Character> listSaves() throws SQLException {
        return characterDao.getCharacters();
    }

    // Charge une partie complète depuis l'id du personnage choisi
    public Object[] loadGame(int characterId) throws SQLException {
        Character player = characterDao.getCharacterWithEquipment(characterId);
        if (player == null) throw new SQLException("Personnage introuvable : " + characterId);
        Board board = boardDao.getBoard(player.getBoardId());
        if (board == null) throw new SQLException("Plateau introuvable : " + player.getBoardId());
        List<Cell> cells = cellDao.getCellsByBoardId(board.getId());
        board.setCells(cells);
        return new Object[]{player, board};
    }

    public void deleteGame(Character player, Board board) throws SQLException {
        // 1. Supprimer l'équipement offensif du joueur
        if (player instanceof Warrior warrior && warrior.getWeapon() != null)
            offensiveEquipmentDao.delete(warrior.getWeapon().getId());
        else if (player instanceof Wizard wizard && wizard.getSpell() != null)
            offensiveEquipmentDao.delete(wizard.getSpell().getId());

        // 2. Supprimer l'équipement défensif du joueur
        if (player.getDefensiveEquipment() != null)
            defensiveEquipmentDao.delete(player.getDefensiveEquipment().getId());

        // 3. Supprimer les ennemis, boîtes et cells
        for (Cell cell : board.getCells()) {
            if (cell.getEnemy() != null) enemyDao.delete(cell.getEnemy().getId());
            if (cell.getBox() != null) {
                if (cell.getBox().getEquipment().isOffensive())
                    offensiveEquipmentDao.delete(cell.getBox().getEquipment().getId());
                else
                    defensiveEquipmentDao.delete(cell.getBox().getEquipment().getId());
                surpriseBoxDao.delete(cell.getBox().getId());
            }
            if (cell.getId() > 0) cellDao.delete(cell.getId());
        }

        // 4. Supprimer le board
        if (board.getId() > 0) boardDao.delete(board.getId());

        // 5. Supprimer le personnage en dernier
        if (player.getId() > 0) characterDao.delete(player.getId());
    }
}
