package javamm.semantics;

import java.util.List;

public class MethodSymbol extends Symbol {
    final private List<Type> parameters;
    final private Symbol returnType;

    public MethodSymbol(Symbol returnSymbol, List<Type> parameters) {
        super(Type.METHOD);
        this.returnType = returnSymbol;
        this.parameters = parameters;
    }

    public List<Type> getParameters() {
        return parameters;
    }

    public Symbol getReturnSymbol() { return returnType; }
}
