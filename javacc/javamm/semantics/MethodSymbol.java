package javamm.semantics;

import java.util.List;

public class MethodSymbol extends Symbol {
    final private List<Type> parameters;
    final private Type returnType;

    public MethodSymbol(Type returnType, List<Type> parameters) {
        super(Type.METHOD);
        this.returnType = returnType;
        this.parameters = parameters;
    }

    public List<Type> getParameters() {
        return parameters;
    }

    public Type getReturnType() { return returnType; }
}
