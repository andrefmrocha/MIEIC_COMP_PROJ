package javamm.cfg;

import javamm.semantics.Symbol;

import java.util.Objects;

public class CFGSymbol {
    private final String identifier;
    private final Symbol symbol;

    public CFGSymbol(String identifier, Symbol symbol) {
        this.identifier = identifier;
        this.symbol = symbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CFGSymbol cfgSymbol = (CFGSymbol) o;
        return Objects.equals(identifier, cfgSymbol.identifier) &&
                Objects.equals(symbol, cfgSymbol.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, symbol);
    }

    public String getIdentifier() {
        return identifier;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return identifier;
    }
}
