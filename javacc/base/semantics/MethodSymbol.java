package base.semantics;

import base.semantics.Symbol;

import java.util.ArrayList;
import java.util.List;

public class MethodSymbol extends Symbol {
    final private List<Type> parameters;

    public MethodSymbol(Type returnType) {
        super(returnType);
        this.parameters = new ArrayList<>();
    }

    public MethodSymbol(Type returnType, List<Type> parameters) {
        super(returnType);
        this.parameters = parameters;
    }

    public List<Type> getParameters() {
        return parameters;
    }
}
