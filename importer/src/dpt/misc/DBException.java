package dpt.misc;

public class DBException extends Exception {
    public DBException(String message) {
        super(message);
    }

    public DBException() {
        super("DBException");
    }
}