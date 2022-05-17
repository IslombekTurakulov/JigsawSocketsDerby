package ru.hse.iuturakulov.jigsawbysockets.utils;

import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class Constants {
    public static final Logger LOGGER = Logger.getLogger(Constants.class.getName());
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    /**
     * The constant SIZE.
     */
    public static final int SIZE = 42;
    /**
     * The constant WIDTH_CELL.
     */
    public static final int WIDTH_CELL = 9;
    /**
     * The constant HEIGHT_CELL.
     */
    public static final int HEIGHT_CELL = 9;
    public static final int MAX_LENGTH_LOGIN = 10;
    public static final int MIN_LENGTH_LOGIN = 4;
    public static AtomicInteger uniqueId = new AtomicInteger();
}
