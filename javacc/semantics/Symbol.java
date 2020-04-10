package semantics;

public class Symbol {
    public enum Type{
        INT, BOOL, OBJ, INT_ARRAY, VOID, METHOD
    }

    final private Type type;
    final private String val;
    final private boolean isInitialized;

    public Type getType() {
        return type;
    }

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
}
