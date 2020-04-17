package base.semantics;

import java.util.List;
import java.util.Objects;

public class MethodIdentifier {
    public String getIdentifier() {
        return identifier;
    }

    final String identifier;
    final List<Symbol.Type> parameters;


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


    public MethodIdentifier(String identifier, List<Symbol.Type> parameters) {
        this.identifier = identifier;
        this.parameters = parameters;
    }

    public List<Symbol.Type> getParameters() {
        return parameters;
    }
}
