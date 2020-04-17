package base;

import base.semantics.ClassSymbol;
import base.semantics.MethodSymbol;
import base.semantics.Symbol;
import base.semantics.Symbol.Type;

public abstract class TypeNode extends SimpleNode {
    protected Type type = Type.VOID;

    public TypeNode(int i) {
        super(i);
    }

    public TypeNode(Parser p, int i) {
        super(p, i);
    }

    public void evaluateChild(SimpleNode child, Type expectedType) throws SemanticsException {
        if (child.id == ParserTreeConstants.JJTIDENTIFIER) {  //Check if the node is a variable
            ASTIdentifier temp = (ASTIdentifier) child;
            String name = temp.identifierName;
            if (table.checkSymbol(name)) { //And check if the identifier already has a symbol declared
                Symbol sym = table.getSymbol(name);
                if (!this.checkType(expectedType, sym))
                    throw new SemanticsException("Identifier '" + name + "' is not of type: " + expectedType.toString() + " in line" + getLine());
            }
        } else if (child instanceof TypeNode) {
            child.setTables(table, methodTable);
            child.eval();
            TypeNode temp = (TypeNode) child;
            if (expectedType != temp.type)
                throw new SemanticsException("Expression is not of type: " + expectedType.toString() + " in line " + getLine() + " got " + temp.type);
        } else
            throw new SemanticsException("Invalid expression");
    }

    boolean checkType(Type type, Symbol symbol) {
        return symbol.getType() == type ||
                (symbol.getType() == Type.METHOD && ((MethodSymbol) symbol).getReturnType() == type);
    }
}
