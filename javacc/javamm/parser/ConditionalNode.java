package javamm.parser;

import javamm.SemanticsException;
import javamm.semantics.Symbol;

import java.io.PrintWriter;

public abstract class ConditionalNode extends TypeNode {
    public ConditionalNode(int i) {
        super(i);
        this.validStatement = true;
    }

    public ConditionalNode(Javamm p, int i) {
        super(p, i);
        this.validStatement = true;
    }

    @Override
    public void eval(Javamm parser) {
        if (this.jjtGetNumChildren() < 1){
            parser.semanticErrors.add(new SemanticsException("Conditional statement must have at least the condition expression", this));
            return;
        }

        SimpleNode condition = (SimpleNode) this.jjtGetChild(0);
        this.evaluateChild(condition, new Symbol(Symbol.Type.BOOL), parser);
    }

    @Override
    public void write(PrintWriter writer) {
        //TODO implement this or leave blank to not call the default one
    }
}
