package exceptions;

public class LecturerNotFoundException extends RuntimeException {

    public LecturerNotFoundException(String message) {
        super(message); // Pass the message to the superclass (RuntimeException)
    }
}
