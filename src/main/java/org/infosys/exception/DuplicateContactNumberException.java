package org.infosys.exception;

public class DuplicateContactNumberException extends RuntimeException {
    public DuplicateContactNumberException(String message) {
        super(message);
    }
}
