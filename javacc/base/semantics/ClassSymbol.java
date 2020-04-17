package base.semantics;

public class ClassSymbol extends Symbol {
    MethodSymbolTable symbolTable = new MethodSymbolTable();
    String extension = null;

    public ClassSymbol() {
        super(Type.CLASS);
    }

    public ClassSymbol(MethodSymbolTable symbolTable) {
        super(Type.CLASS);
        this.symbolTable = symbolTable;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }

    public MethodSymbolTable getSymbolTable() {
        return symbolTable;
    }
}
