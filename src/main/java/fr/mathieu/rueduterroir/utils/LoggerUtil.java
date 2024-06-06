package fr.mathieu.rueduterroir.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtil {
    private static final Logger log = LoggerFactory.getLogger(LoggerUtil.class);

    public static void Success(String message) {
        log.info("\u001B[32m[SUCCESS] {}", message);
    }

    public static void Warning(String message) {
        log.warn("\u001B[33m[WARNING] {}", message);
    }

    public static void Error(String message) {
        log.error("\u001B[31m[ERROR] {}", message);
    }
}
