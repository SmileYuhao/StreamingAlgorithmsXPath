package Exceptions;

public class BadFormedException extends RuntimeException {
    public BadFormedException(int lineNumber) {
        super("Bad XML Formed in line: " + lineNumber);
    }
}
