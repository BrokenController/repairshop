package com.repairshop.filehandler;

public class FileHandlerEmptyInputException extends Exception {
    public FileHandlerEmptyInputException() {
    }

    public FileHandlerEmptyInputException(String message) {
        super(message);
    }

    public FileHandlerEmptyInputException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileHandlerEmptyInputException(Throwable cause) {
        super(cause);
    }

    public FileHandlerEmptyInputException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
