package javamm;

public class SemanticsException extends Exception {
    private final String error;
    public SemanticsException(String error){
        this.error = error;
    }

    public String toString() {
        return error;
    }
}
