import semantics.Symbol;
import semantics.Symbol.Type;

public abstract class BinaryOperatorNode extends TypeNode {
    private Type operandType;

    abstract public Type[] getSupportedTypes();

    public BinaryOperatorNode(int i, Type type, Type op) {
        super(i);
        this.type = type;
        this.operandType = op;
    }

    public BinaryOperatorNode(Parser p, int i, Type type, Type op) {
        super(p, i);
        this.type = type;
        this.operandType = op;
    }

    @Override
    public void eval() throws SemanticsException {
        if (this.jjtGetNumChildren() != 2) throw new SemanticsException("Operator requires two operands");

        SimpleNode leftOperand = (SimpleNode) this.jjtGetChild(0);
        SimpleNode rightOperand = (SimpleNode) this.jjtGetChild(1);

        checkOperand(leftOperand);
        checkOperand(rightOperand);
    }

    public void checkOperand(SimpleNode operand) throws SemanticsException {
        if (operand.id == ParserTreeConstants.JJTIDENTIFIER) {  //Check if the node is a variable
            ASTIdentifier temp = (ASTIdentifier) operand;
            String name = temp.identifierName;
            if (table.checkSymbol(name)) { //And check if the identifier already has a symbol declared
                Symbol leftSymbol = table.getSymbol(name);
                if (operandType != leftSymbol.getType())
                    throw new SemanticsException("Left operand '" + name + "' type doesn't match operand Type: " + operandType.toString());
            }
        } else if (operand instanceof TypeNode && operand.id != ParserTreeConstants.JJTMETHODCALL) { //TODO: REMOVE METHOD CALL CONDITION
            TypeNode temp = (TypeNode) operand;
            if (operandType != temp.type)
                throw new SemanticsException("Left operand type doesn't match the operand Type: " + operandType.toString());
        } else if (operand.id != ParserTreeConstants.JJTMETHODCALL)
            throw new SemanticsException("Invalid operand"); //TODO: REMOVE METHOD CALL CONDITION
    }
}
