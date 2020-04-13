package semantics;

import java.util.ArrayList;
import java.util.List;

public class Symbol {
    public enum Type{
        INT, BOOL, OBJ, INT_ARRAY, VOID
    }

    final protected Type type;
    final protected String val;
    final protected boolean isInitialized;

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
