package com.repairshop.filehandler;

public class FileHandlerException extends Exception {
    public FileHandlerException() {
    }

    public FileHandlerException(String message) {
        super(message);
    }

    public FileHandlerException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileHandlerException(Throwable cause) {
        super(cause);
    }

    public FileHandlerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
