package fr.campus.dndgame.main.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Classe utilitaire pour l'initialisation de la base de données.
 * Crée les tables nécessaires au jeu si elles n'existent pas encore.
 * Utilise la clause IF NOT EXISTS pour éviter les erreurs si les tables sont déjà présentes.
 *
 * @author CorentinFERRY
 * @version 1.0
 */
public class DatabaseInitializer {

    /**
     * Initialise la base de données en créant toutes les tables nécessaires.
     * Les tables sont créées dans l'ordre des dépendances (clés étrangères).
     * Cette méthode peut être appelée à chaque démarrage sans risque.
     *
     * @param con La connexion à la base de données
     */
    public static void init(Connection con) {
        String sql = """ 
                CREATE TABLE IF NOT EXISTS boards (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(50),
                    size INT
                );
                
                CREATE TABLE IF NOT EXISTS enemies (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(100),
                    health TINYINT,
                    maxHealth TINYINT,
                    attack TINYINT,
                    defense TINYINT
                );
                
                CREATE TABLE IF NOT EXISTS offensiveEquipments (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    type VARCHAR(50),
                    name VARCHAR(50),
                    effect INT
                );
                
                CREATE TABLE IF NOT EXISTS defensiveEquipments (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    type VARCHAR(50),
                    name VARCHAR(50),
                    effect INT
                );
                
                CREATE TABLE IF NOT EXISTS surpriseBoxes (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(50),
                    offensiveEquipment_id INT DEFAULT NULL,
                    defensiveEquipment_id INT DEFAULT NULL,
                    FOREIGN KEY (offensiveEquipment_id) REFERENCES offensiveEquipments(id)
                        ON DELETE SET NULL,
                    FOREIGN KEY (defensiveEquipment_id) REFERENCES defensiveEquipments(id)
                        ON DELETE SET NULL
                );
                
                CREATE TABLE IF NOT EXISTS characters (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    type VARCHAR(50),
                    name VARCHAR(50),
                    health TINYINT,
                    maxHealth TINYINT,
                    attack TINYINT,
                    defense TINYINT,
                    position INT,
                    board_id INT DEFAULT NULL,
                    offensiveEquipment_id INT DEFAULT NULL,
                    defensiveEquipment_id INT DEFAULT NULL,
                    FOREIGN KEY (board_id) REFERENCES boards(id)
                        ON DELETE SET NULL,
                    FOREIGN KEY (offensiveEquipment_id) REFERENCES offensiveEquipments(id)
                        ON DELETE SET NULL,
                    FOREIGN KEY (defensiveEquipment_id) REFERENCES defensiveEquipments(id)
                        ON DELETE SET NULL
                );
                
                CREATE TABLE IF NOT EXISTS cells (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    position INT,
                    board_id INT ,
                    enemy_id INT  DEFAULT NULL,
                    surpriseBox_id INT DEFAULT NULL,
                    FOREIGN KEY (board_id) REFERENCES boards(id)
                        ON DELETE CASCADE,
                    FOREIGN KEY (enemy_id) REFERENCES enemies(id)
                        ON DELETE SET NULL,
                    FOREIGN KEY (surpriseBox_id) REFERENCES surpriseBoxes(id)
                        ON DELETE SET NULL
                );
                """;
        try (Statement stmt = con.createStatement()) {
            for (String query : sql.split(";")) {
                String trimmed = query.trim();
                if (!trimmed.isEmpty()) {
                    stmt.execute(trimmed);
                }
            }
            System.out.println("Base de données initialisée.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
