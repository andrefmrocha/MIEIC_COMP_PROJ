package javamm.parser;

import com.sun.jdi.BooleanValue;
import javamm.SemanticsException;
import javamm.semantics.StackUsage;
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

        // check types
        this.evaluateChild(leftOperand, new Symbol(operandType), parser);
        this.evaluateChild(rightOperand, new Symbol(operandType), parser);
    }

    @Override
    public void write(PrintWriter writer) {
        SimpleNode leftOperand = (SimpleNode) this.jjtGetChild(0);
        SimpleNode rightOperand = (SimpleNode) this.jjtGetChild(1);

        // write operands to stack
        leftOperand.write(writer);
        rightOperand.write(writer);

        //then each operation will use this part and add its own action
    }

    @Override
    protected void calculateStackUsage(StackUsage stackUsage) {
        SimpleNode leftOperand = (SimpleNode) this.jjtGetChild(0);
        SimpleNode rightOperand = (SimpleNode) this.jjtGetChild(1);

        leftOperand.calculateStackUsage(stackUsage);
        rightOperand.calculateStackUsage(stackUsage);

        stackUsage.dec(1); // operation will pop the 2 operands and leave the result (-2+1 = -1)
    }
}
