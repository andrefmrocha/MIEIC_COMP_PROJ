package javamm.semantics;


import javamm.parser.JavammTreeConstants;
import javamm.parser.SimpleNode;

public class Symbol {
    public enum Type{
        INT, BOOL, INT_ARRAY, VOID, MAIN, METHOD, CLASS
    }

    final protected Type type;
    final protected String val;
    protected boolean isInitialized;

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
        switch (node.getId()){
            case JavammTreeConstants.JJTVOID:
                return Type.VOID;
            case JavammTreeConstants.JJTINT:
                return Type.INT;
            case JavammTreeConstants.JJTBOOLEAN:
                return Type.BOOL;
            case JavammTreeConstants.JJTINTARRAY:
                return Type.INT_ARRAY;
            case JavammTreeConstants.JJTIDENTIFIER:
                return Type.CLASS;
        }
        return null;
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    public void setInitialized() { isInitialized = true;}
}
