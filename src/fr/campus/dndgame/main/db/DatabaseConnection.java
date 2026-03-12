package fr.campus.dndgame.main.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * Classe gérant la connexion à la base de données.
 * Établit une connexion statique à la base de données en utilisant les
 * paramètres
 * définis dans le fichier de configuration db.properties.
 * 
 * @author CorentinFERRY
 * @version 1.0
 */
public class DatabaseConnection {
    /**
     * Connexion statique à la base de données initialisée au chargement de la classe.
     * Charge les paramétres de connexion depuis le fichier db.properties.
     */
    private static Connection con = null;
    static {
        Properties props = new Properties();

        try (InputStream input = DatabaseConnection.class
                .getClassLoader()
                .getResourceAsStream("db.properties")) {

            props.load(input);

            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String pass = props.getProperty("db.pass");

            con = DriverManager.getConnection(url, user, pass);
            DatabaseInitializer.init(con);

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retourne la connexion à la base de données.
     * 
     * @return L'objet Connection statique vers la base de données
     */
    public static Connection getConnection() {
        return con;
    }

}
