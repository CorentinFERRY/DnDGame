package fr.campus.dndgame.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DatabaseConnection {
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

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection() {
        return con;
    }

    public void testSelectAll() {
        String query = "SELECT * FROM characters";

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                System.out.println(
                    rs.getInt("id") +
                    " | name: " + rs.getString("name") +
                    " | vie: " + rs.getInt("health") +
                    " | attaque: " + rs.getInt("attack")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
