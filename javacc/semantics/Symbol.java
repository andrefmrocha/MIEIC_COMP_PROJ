package semantics;

import java.util.ArrayList;
import java.util.List;

public class Symbol {
    public enum Type{
        INT, BOOL, OBJ, INT_ARRAY, VOID, METHOD
    }

    final protected Type type;
    final protected String val;
    final protected boolean isInitialized;

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

    public static Type getNodeSymbolType(SimpleNode node) {
        switch (node.id){
            case ParserTreeConstants.JJTVOID:
                return Type.VOID;
            case ParserTreeConstants.JJTINT:
                return Type.INT;
            case ParserTreeConstants.JJTBOOLEAN:
                return Type.BOOL;
            case ParserTreeConstants.JJTINTARRAY:
                return Type.INT_ARRAY;
            case ParserTreeConstants.JJTIDENTIFIER:
                return Type.OBJ;
        }
        return null;
    }
}
