package com.repairshop.parser;

public class CSVParserException extends Exception{
    public CSVParserException() {
    }

    public CSVParserException(String message) {
        super(message);
    }

    public CSVParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public CSVParserException(Throwable cause) {
        super(cause);
    }

    public CSVParserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
