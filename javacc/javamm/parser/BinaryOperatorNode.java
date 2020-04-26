package javamm.parser;

import javamm.SemanticsException;
import javamm.semantics.Symbol;
import javamm.semantics.Symbol.Type;

import java.io.PrintWriter;

public abstract class BinaryOperatorNode extends TypeNode {
    private Type operandType;

    abstract public Type[] getSupportedTypes();

    public BinaryOperatorNode(int i, Type type, Type op) {
        super(i);
        this.type = type;
        this.operandType = op;
    }

    public BinaryOperatorNode(Javamm p, int i, Type type, Type op) {
        super(p, i);
        this.type = type;
        this.operandType = op;
    }

    @Override
    public void eval(Javamm parser) {
        if (this.jjtGetNumChildren() != 2){
            parser.semanticErrors.add(new SemanticsException("Operator requires two operands", this));
            return;
        }

        SimpleNode leftOperand = (SimpleNode) this.jjtGetChild(0);
        SimpleNode rightOperand = (SimpleNode) this.jjtGetChild(1);

        this.evaluateChild(leftOperand, new Symbol(operandType), parser);
        this.evaluateChild(rightOperand, new Symbol(operandType), parser);
    }

    @Override
    public void write(PrintWriter writer) {
        //TODO implement this or leave blank to not call the default one
    }
}
