import semantics.Symbol;
import semantics.Symbol.Type;

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
                if (expectedType != sym.getType())
                    throw new SemanticsException("Identifier '" + name + "' is not of type: " + expectedType.toString());
            }
        } else if (child.id == ParserTreeConstants.JJTMETHODCALL) { //TODO: REMOVE METHOD CALL CONDITION
            final ASTMethodCall call = (ASTMethodCall) child;
            call.eval();

            if(call.type != expectedType)
                throw new SemanticsException("Method call not does return " + type.toString());

        } else if (child instanceof TypeNode) { //TODO: REMOVE METHOD CALL CONDITION
            TypeNode temp = (TypeNode) child;
            if (expectedType != temp.type)
                throw new SemanticsException("Expression is not of type: " + expectedType.toString());
        } else
            new SemanticsException("Invalid expression"); //TODO: REMOVE METHOD CALL CONDITION
    }
}
