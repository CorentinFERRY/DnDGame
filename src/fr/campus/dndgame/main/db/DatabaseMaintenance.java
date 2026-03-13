package fr.campus.dndgame.main.db;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Classe utilitaire de maintenance de la base de données.
 * Fournit des opérations de nettoyage pour supprimer les entrées orphelines
 * qui ne sont plus référencées par aucune entité active du jeu.
 */
public class DatabaseMaintenance {

    private final Connection con = DatabaseConnection.getConnection();

    /**
     * Supprime toutes les entrées orphelines de la base de données.
     * Une entrée est considérée orpheline si elle n'est plus référencée
     * par aucune entité active.
     * L'ordre de suppression respecte les contraintes de clés étrangères.
     * Entités nettoyées :
     * - Équipements offensifs non équipés par un personnage et absents de toute boîte surprise
     * - Équipements défensifs non équipés par un personnage et absents de toute boîte surprise
     * - Ennemis non associés à une case du plateau
     * - Boîtes surprises non associées à une case du plateau
     *
     * @throws SQLException en cas d'erreur lors de l'accès à la base de données
     */
    public void purgeOrphans() throws SQLException {
        con.prepareStatement("""
            DELETE FROM offensiveEquipments
            WHERE id NOT IN (SELECT offensiveEquipment_id FROM characters WHERE offensiveEquipment_id IS NOT NULL)
            AND id NOT IN (SELECT offensiveEquipment_id FROM surpriseBoxes WHERE offensiveEquipment_id IS NOT NULL)
        """).executeUpdate();

        con.prepareStatement("""
            DELETE FROM defensiveEquipments
            WHERE id NOT IN (SELECT defensiveEquipment_id FROM characters WHERE defensiveEquipment_id IS NOT NULL)
            AND id NOT IN (SELECT defensiveEquipment_id FROM surpriseBoxes WHERE defensiveEquipment_id IS NOT NULL)
        """).executeUpdate();

        con.prepareStatement("""
            DELETE FROM enemies
            WHERE id NOT IN (SELECT enemy_id FROM cells WHERE enemy_id IS NOT NULL)
        """).executeUpdate();

        con.prepareStatement("""
            DELETE FROM surpriseBoxes
            WHERE id NOT IN (SELECT surpriseBox_id FROM cells WHERE surpriseBox_id IS NOT NULL)
        """).executeUpdate();
    }
}
