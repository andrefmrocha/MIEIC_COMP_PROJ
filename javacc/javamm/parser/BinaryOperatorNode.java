package javamm.parser;

import javamm.SemanticsException;
import javamm.semantics.Symbol;
import javamm.semantics.Symbol.Type;

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

        this.evaluateChild(leftOperand, new Symbol(operandType));
        this.evaluateChild(rightOperand, new Symbol(operandType));
    }
}
