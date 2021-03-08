package com.repairshop.parser;

public class ArgumentsParserException extends Exception {
    public ArgumentsParserException() {
    }

    public ArgumentsParserException(String message) {
        super(message);
    }

    public ArgumentsParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArgumentsParserException(Throwable cause) {
        super(cause);
    }

    public ArgumentsParserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
