package base.semantics;

import java.util.HashMap;

public class MethodSymbolTable {
    final private HashMap<MethodIdentifier, MethodSymbol> table = new HashMap<>();
    private MethodSymbolTable parent = null;

    public MethodSymbolTable(){}

    public MethodSymbolTable(MethodSymbolTable parent){
        this.parent = parent;
    }

    public HashMap<MethodIdentifier, MethodSymbol> getTable() {
        return table;
    }

    public MethodSymbolTable getParent()
    {
        return parent;
    }
    public MethodSymbol getSymbol(MethodIdentifier key) {
        if(table.containsKey(key))
            return table.get(key);
        return parent != null ? parent.getSymbol(key) : null;
    }

    public boolean checkSymbol(MethodIdentifier symbol) {
        if (table.containsKey(symbol))
            return true;
        else if (parent != null)
            return parent.checkSymbol(symbol);
        return false;
    }

    public boolean checkSymbolWithinScope(String symbol) {
        return table.containsKey(symbol);
    }

    public void putSymbol(MethodIdentifier name, MethodSymbol symbol) {
        table.put(name, symbol);
    }
}
