package base;

public class SemanticsException extends Exception {
    private final String error;
    SemanticsException(String error){
        this.error = error;
    }

    public String toString() {
        return error;
    }
}