import semantics.Symbol.Type;

public class TypeNode extends SimpleNode {
    protected Type type = Type.VOID;

    public TypeNode(int i) {
        super(i);
    }

    public TypeNode(Parser p, int i) {
        super(p, i);
    }
}
