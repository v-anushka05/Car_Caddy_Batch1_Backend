package org.infosys.exception;


// for invalid user name and password
public class InvalidLoginException extends Exception {
    public InvalidLoginException(String message) {
        super(message);
    }
}
