package base;

import base.semantics.Symbol;

public abstract class ConditionalNode extends TypeNode {
    public ConditionalNode(int i) {
        super(i);
    }

    public ConditionalNode(Parser p, int i) {
        super(p, i);
    }

    @Override
    public void eval() throws SemanticsException {
        if(this.jjtGetNumChildren() < 1) throw new SemanticsException("Conditional statement must have at least the condition expression");

        SimpleNode condition = (SimpleNode) this.jjtGetChild(0);
        this.evaluateChild(condition, Symbol.Type.BOOL);

        for(int i = 1; i < this.jjtGetNumChildren(); i++) {
            SimpleNode child = (SimpleNode) this.jjtGetChild(i);
            child.setTables(table, methodTable);
            child.eval();
        }
    }
}
