package semantics;

import java.util.ArrayList;
import java.util.List;

public class Symbol {
    public enum Type{
        INT, BOOL, OBJ, INT_ARRAY, VOID
    }

    final private Type type;
    final private String val;
    final private boolean isInitialized;
    private List<Type> parameters = new ArrayList<>();

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

    public Symbol(Type type, String val, boolean isInitialized, List<Type> parameters) {
        this.type = type;
        this.val = val;
        this.isInitialized = isInitialized;
        this.parameters = parameters;
    }

    public Type getType() {
        return this.type;
    }

    public List<Type> getParameters() {
        return parameters;
    }

    public static Type getNodeSymbolType(SimpleNode node) {
        switch (node.id){
            case JJTVOID:
                return Type.VOID;
            case JJTINT:
                return Type.INT;
            case JJTBOOLEAN:
                return Type.BOOL;
            case JJTINTARRAY:
                return Type.INT_ARRAY;
            case JJTIDENTIFIER:
                returnType = Type.OBJ;
        }
        return null;
    }
}
