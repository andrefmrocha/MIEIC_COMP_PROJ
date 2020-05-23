package javamm.semantics;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MethodIdentifier {
    public String getIdentifier() {
        return identifier;
    }

    final String identifier;
    final List<Symbol.Type> parameters;

    public List<Symbol.Type> convertParameters(){
        return parameters.stream().map(type -> type == Symbol.Type.OBJ ? Symbol.Type.CLASS : type
        ).collect(Collectors.toList());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MethodIdentifier that = (MethodIdentifier) o;
        return Objects.equals(identifier, that.identifier) &&
                Objects.equals(convertParameters(), that.parameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, convertParameters());
    }


    public MethodIdentifier(String identifier, List<Symbol.Type> parameters) {
        this.identifier = identifier;
        this.parameters = parameters;
    }

    public List<Symbol.Type> getParameters() {
        return parameters;
    }
}
