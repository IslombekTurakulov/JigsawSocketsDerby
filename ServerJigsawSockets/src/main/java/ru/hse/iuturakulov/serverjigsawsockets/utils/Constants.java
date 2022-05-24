package ru.hse.iuturakulov.serverjigsawsockets.utils;

/**
 * The type Constants.
 */
public class Constants {
    /**
     * The constant timeCurrent.
     */
    public static String timeCurrent;
    public static final String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    // the database name
    public static final String dbName = "server";
    // define the Derby connection URL to use
    public static final String connectionURL = "jdbc:derby:%s;create=true".formatted(dbName);
}
