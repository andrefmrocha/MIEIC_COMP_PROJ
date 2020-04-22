package javamm.semantics;

import java.util.HashMap;

public class SymbolTable {
    final private HashMap<String, Symbol> table = new HashMap<>();
    private SymbolTable parent = null;

    public SymbolTable(){}

    public SymbolTable(SymbolTable parent){
        this.parent = parent;
    }

    public HashMap<String, Symbol> getTable() {
        return table;
    }

    public SymbolTable getParent()
    {
        return parent;
    }
    public Symbol getSymbol(String key) {
        if(table.containsKey(key))
            return table.get(key);
        return parent != null ? parent.getSymbol(key) : null;
    }

    public boolean checkSymbol(String symbol) {
        if (table.containsKey(symbol))
            return true;
        else if (parent != null)
            return parent.checkSymbol(symbol);
        return false;
    }

    public boolean checkSymbolWithinScope(String symbol) {
        return table.containsKey(symbol);
    }

    public void putSymbol(String name, Symbol symbol) {
        table.put(name, symbol);
    }
}