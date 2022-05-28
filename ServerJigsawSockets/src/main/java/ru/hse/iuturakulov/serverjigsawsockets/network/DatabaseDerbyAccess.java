package ru.hse.iuturakulov.serverjigsawsockets.network;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
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
    private Statement statement;

    /**
     * Instantiates a new Database derby access.
     */
    public DatabaseDerbyAccess() {
        try {
            String databaseTable = "CREATE TABLE RATING_LIST  "
                                   + "(GAME_ID INT NOT NULL GENERATED ALWAYS AS IDENTITY "
                                   + "   CONSTRAINT GAME_PK PRIMARY KEY, "
                                   + " LOGIN_PLAYER VARCHAR(32) NOT NULL, "
                                   + " END_GAME_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
                                   + " PLACED_BLOCKS INT NOT NULL, "
                                   + " TIME_GAME TIME NOT NULL) ";
            // Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            connection = DriverManager.getConnection("jdbc:derby:server;create=true");
            if (!connection.getMetaData().getTables(null, "APP", "RATING_LIST", null).next()) {
                System.out.println("Table doesn't exist, Creating Table...");
                statement = connection.createStatement();
                statement.execute(databaseTable);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(DatabaseDerbyAccess.class.getName()).log(Level.SEVERE, e.getMessage());
        }
    }

    public Statement getStatement() {
        return statement;
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
