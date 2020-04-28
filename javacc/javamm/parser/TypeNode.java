package javamm.parser;

import javamm.SemanticsException;
import javamm.semantics.ClassSymbol;
import javamm.semantics.MethodSymbol;
import javamm.semantics.Symbol;
import javamm.semantics.Symbol.Type;


public abstract class TypeNode extends SimpleNode {
    protected Type type = Type.VOID;

    public TypeNode(int i) {
        super(i);
    }

    public TypeNode(Javamm p, int i) {
        super(p, i);
    }

    public void evaluateChild(SimpleNode child, Symbol symbol, Javamm parser) {
        Type expectedType = symbol.getType();
        if (child.id == JavammTreeConstants.JJTIDENTIFIER) {  //Check if the node is a variable
            ASTIdentifier temp = (ASTIdentifier) child;
            temp.setTables(this.table, this.methodTable);
            String name = temp.identifierName;
            if (!table.checkSymbol(name)) { //And check if the identifier already has a symbol declared
                parser.semanticErrors.add(new SemanticsException("Did not find variable named  " + name, child));
                return;
            }

            Symbol sym = table.getSymbol(name);
            if (!this.checkType(expectedType, sym)) {
                parser.semanticErrors.add(new SemanticsException("Variable '" + name + "' is not of type: " + expectedType.toString(), child));
                return;
            }

            if (!sym.isInitialized()) {
                parser.semanticWarnings.add(new SemanticsException("Variable " + name + " is not initialized", child));
            }
        } else if (child instanceof TypeNode) {
            child.setTables(table, methodTable);
            child.eval(parser);
            Type childType = ((TypeNode) child).type;
            if( !(childType == Type.CLASS && expectedType == Type.OBJ) && expectedType != childType)
                parser.semanticErrors.add(new SemanticsException("Expression is not of type: " + expectedType.toString() + " got " + childType.toString(), child));
            else if (expectedType == Type.OBJ ) {
                // compare classes and check if extends
                ClassSymbol expectedClass = (ClassSymbol) symbol;
                ClassSymbol childClassSymbol = null;
                if (child instanceof ASTClass)
                    childClassSymbol = ((ASTClass) child).classSymbol;
                else if (child instanceof ASTNew)
                    childClassSymbol = ((ASTNew) child).classSymbol;
                else {
                    parser.semanticErrors.add(new SemanticsException("Unknown node with type CLASS", child));
                    return;
                }

                if (!childClassSymbol.derivesFrom(expectedClass)) {
                    parser.semanticErrors.add(new SemanticsException("Class " + childClassSymbol.getClassName() + " does not derive from " + expectedClass.getClassName(), child));
                    return;
                }
            }
        } else
            parser.semanticErrors.add(new SemanticsException("Invalid expression", child));
    }

    boolean checkType(Type type, Symbol symbol) {
        return symbol.getType() == type ||
                (symbol.getType() == Type.METHOD && ((MethodSymbol) symbol).getReturnType() == type);
    }
}
