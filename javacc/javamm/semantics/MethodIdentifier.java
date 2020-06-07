package javamm.semantics;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Wrapper around the identifier of the method, storing the parameter's symbols
 * and the identifier of the method
 */
public class MethodIdentifier {
    public String getIdentifier() {
        return identifier;
    }

    final String identifier;
    final List<Symbol> parameters;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MethodIdentifier that = (MethodIdentifier) o;
        return Objects.equals(identifier, that.identifier) &&
                Objects.equals(parameters, that.parameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, parameters);
    }


    public MethodIdentifier(String identifier, List<Symbol> parameters) {
        this.identifier = identifier;
        this.parameters = parameters;
    }

    public List<Symbol> getParameters() {
        return parameters;
    }

    public List<Symbol.Type> getParametersTypes(){
        return parameters.stream().map(Symbol::getType).collect(Collectors.toList());
    }
}
