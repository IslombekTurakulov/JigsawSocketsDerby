package ru.hse.iuturakulov.jigsawbysockets.utils;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class Constants {
    public static final Logger LOGGER = Logger.getLogger(Constants.class.getName());
    public static AtomicInteger uniqueId = new AtomicInteger();
}
