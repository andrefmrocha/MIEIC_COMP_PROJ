package javamm.semantics;

import java.util.List;
import java.util.Objects;

public class MethodSymbol extends Symbol {
    final private List<Symbol> parameters;
    final private Symbol returnType;

    public MethodSymbol(Symbol returnSymbol, List<Symbol> parameters) {
        super(Type.METHOD);
        this.returnType = returnSymbol;
        this.parameters = parameters;
    }

    public List<Symbol> getParameters() {
        return parameters;
    }

    public Symbol getReturnSymbol() { return returnType; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MethodSymbol that = (MethodSymbol) o;
        return Objects.equals(parameters, that.parameters) &&
                Objects.equals(returnType, that.returnType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), parameters, returnType);
    }
}
