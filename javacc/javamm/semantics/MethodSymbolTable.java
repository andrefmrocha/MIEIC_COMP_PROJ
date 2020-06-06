package javamm.semantics;

import java.util.HashMap;
import java.util.Map;

/**
 * Symbol table specialized to store methods
 */
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

    public void print() {
        for(Map.Entry<MethodIdentifier,MethodSymbol> entry : table.entrySet())
            System.out.println(entry.getKey().getIdentifier() + " | " + entry.getKey().getParameters());
        if( parent != null) {
            for(Map.Entry<MethodIdentifier,MethodSymbol> entry : parent.getTable().entrySet())
                System.out.println(entry.getKey().getIdentifier() + " | " + entry.getKey().getParameters());
        }
    }
}
