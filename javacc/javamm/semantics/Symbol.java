package javamm.semantics;


import javamm.parser.JavammTreeConstants;
import javamm.parser.SimpleNode;

public class Symbol {
    private int stackPos = -1;

    public enum Type {
        INT, BOOL, INT_ARRAY, VOID, MAIN, METHOD, CLASS, OBJ
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

    public Symbol(Type type, String s, boolean isInitialized, int stackPos) {
        this(type, s, isInitialized);
        this.stackPos = stackPos;
    }

    public static Type getNodeSymbolType(SimpleNode node) {
        switch (node.getId()) {
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

    public static String getJVMTypeByType(Type type) {
        switch (type) {
            case INT:
                return "I";
            case BOOL:
                return "Z";
            case INT_ARRAY:
                return "[I";
            case VOID:
                return "V";
        }
        return null;
    }

    public static String getJVMPrefix(Type type) {
        switch (type) {
            case INT:
                return "i";
            case BOOL:
                return "b";
            case INT_ARRAY:
            case CLASS:
            case OBJ:
                return "a";
        }
        return null;
    }

    public String getJVMType() {
        return getJVMTypeByType(type);
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    public void setInitialized() {
        isInitialized = true;
    }

    public int getStackPos() {
        return stackPos;
    }

    public void setStackPos(int stackPos) {
        if(stackPos != -1)
            this.stackPos = stackPos;
    }
}
