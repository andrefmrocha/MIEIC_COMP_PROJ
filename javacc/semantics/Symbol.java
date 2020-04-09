package semantics;

public class Symbol {
    public enum Type{
        INT, BOOL, OBJ, INT_ARRAY, VOID
    }

    final private Type type;
    final private String val;
    final private boolean isInitialized;

    public Symbol(Type type) {
        this(type, "");
    }

    public Symbol(Type type, String val) {
        this(type, val, false);
    }

    public Symbol(Type type, String val, boolean isInitialized) {
        this.type = type;
        this.val = val;
        this.isInitialized = isInitialized;
    }

    public Type getType() {
        return this.type;
    }
}
