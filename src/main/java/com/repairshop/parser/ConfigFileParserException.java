package com.repairshop.parser;

public class ConfigFileParserException extends Exception {
    public ConfigFileParserException() {
    }

    public ConfigFileParserException(String message) {
        super(message);
    }

    public ConfigFileParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigFileParserException(Throwable cause) {
        super(cause);
    }

    public ConfigFileParserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
