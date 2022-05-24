package ru.hse.iuturakulov.serverjigsawsockets.network;

import ru.hse.iuturakulov.serverjigsawsockets.utils.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The type Database derby access.
 *
 * @author IslombekTurakulov
 * @version 1.0
 * @see Client
 * @see ClientHandler
 */
public class DatabaseDerbyAccess {

    private Connection connection;

    /**
     * Instantiates a new Database derby access.
     */
    public DatabaseDerbyAccess() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            connection = DriverManager.getConnection(Constants.connectionURL);
        } catch (Exception e) {
            Logger.getLogger(DatabaseDerbyAccess.class.getName()).log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * Closes database connection.
     */
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Failed to close the connection");
        }
    }

    /**
     * Gets connection.
     *
     * @return the connection
     */
    public Connection getConnection() {
        return connection;
    }
}
