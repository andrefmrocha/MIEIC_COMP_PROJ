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

    public void evaluateChild(SimpleNode child, Symbol symbol) throws SemanticsException {
        Type expectedType = symbol.getType();
        if (child.id == ParserTreeConstants.JJTIDENTIFIER) {  //Check if the node is a variable
            ASTIdentifier temp = (ASTIdentifier) child;
            String name = temp.identifierName;
            if (!table.checkSymbol(name)) //And check if the identifier already has a symbol declared
                throw new SemanticsException("Did not find variable named  " + name);

            Symbol sym = table.getSymbol(name);
            if (!this.checkType(expectedType, sym))
                throw new SemanticsException("Variable '" + name + "' is not of type: " + expectedType.toString() + " in line" + getLine());

            if (!sym.isInitialized())
                throw new SemanticsException("Variable " + name + " is not initialized");
        } else if (child instanceof TypeNode) {
            child.setTables(table, methodTable);
            child.eval();
            Type childType = ((TypeNode) child).type;
            if (expectedType != childType)
                throw new SemanticsException("Expression is not of type: " + expectedType.toString() + " in line " + getLine() + " got " + childType.toString());
            else if (expectedType == Type.CLASS) {
                // compare classes and check if extends
                ClassSymbol expectedClass = (ClassSymbol) symbol;
                ClassSymbol childClassSymbol = null;
                if (child instanceof ASTClass)
                    childClassSymbol = ((ASTClass) child).classSymbol;
                else if (child instanceof ASTNew)
                    childClassSymbol = ((ASTNew) child).classSymbol;
                else
                    throw new SemanticsException("Unknown node with type CLASS");

                if (!childClassSymbol.derivesFrom(expectedClass))
                    throw new SemanticsException("Class " + childClassSymbol.getClassName() + " does not derive from " + expectedClass.getClassName());
            }
        } else
            throw new SemanticsException("Invalid expression");
    }

    boolean checkType(Type type, Symbol symbol) {
        return symbol.getType() == type ||
                (symbol.getType() == Type.METHOD && ((MethodSymbol) symbol).getReturnType() == type);
    }
}
