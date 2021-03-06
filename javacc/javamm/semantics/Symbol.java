package javamm.semantics;


import javamm.parser.JavammTreeConstants;
import javamm.parser.SimpleNode;

import java.util.Objects;

/**
 * Container for the Symbol's used in a jmm file.
 */
public class Symbol {
    private int stackPos = -1;

    public enum Type {
        INT, BOOL, INT_ARRAY, VOID, MAIN, METHOD, CLASS, OBJ
    }

    final protected Type type;
    protected boolean isInitialized;
    protected boolean hasChanged = false;
    protected boolean isUsed = false;
    protected int value = -1;

    public Type getType() {
        return type;
    }

    public Symbol(Type type) {
        this(type, false);
    }

    public Symbol(Type type, boolean isInitialized) {
        this.type = type;
        this.isInitialized = isInitialized;
    }

    public Symbol(Type type, boolean isInitialized, int stackPos) {
        this(type, isInitialized);
        this.stackPos = stackPos;
    }

    /**
     * Gets the type of the node
     * @param node - the given node
     * @return the type of the node
     */
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

    /**
     * Gets the JVM prefix associated to a variable
     * @param type - the type of the variable
     * @return the JVM prefix
     */
    public static String getJVMPrefix(Type type) {
        switch (type) {
            case INT:
            case BOOL:
                return "i";
            case INT_ARRAY:
            case CLASS:
            case OBJ:
                return "a";
            case VOID:
                return "";

        }
        return null;
    }

    /**
     * Gets the JVM type associated to the symbol
     * @return the symbol's string
     */
    public String getJVMType() {
        switch (type) {
            case INT:
                return "I";
            case BOOL:
                return "Z";
            case INT_ARRAY:
                return "[I";
            case VOID:
            case OBJ:
            case CLASS:
                return "V";
        }
        return null;
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    public boolean hasChanged() {return hasChanged;}

    public void didChange() {hasChanged = true;}

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed() {
        isUsed = true;
    }

    public int getValue() {return value;}

    public void setValue(int value) {
        this.value = value;
    }

    public void setInitialized() {
        isInitialized = true;
    }

    public void setInitialized(boolean init) {
        isInitialized = init;
    }

    public int getStackPos() {
        return stackPos;
    }

    public void setStackPos(int stackPos) {
        if (stackPos != -1)
            this.stackPos = stackPos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Symbol symbol = (Symbol) o;
        return type == symbol.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
}
