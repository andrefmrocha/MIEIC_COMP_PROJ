package semantics;

import java.util.HashMap;

public class SymbolTable {
    final private HashMap<String, Symbol> table = new HashMap<>();
    private SymbolTable parent = null;

    SymbolTable(){}

    SymbolTable(SymbolTable parent){
        this.parent = parent;
    }


    public boolean checkSymbol(String symbol) {
        if (table.containsKey(symbol))
            return true;
        else if (parent != null)
            return parent.checkSymbol(symbol);
        return false;
    }

    public void addSymbol(String name, Symbol symbol) {
        table.put(name, symbol);
    }
}