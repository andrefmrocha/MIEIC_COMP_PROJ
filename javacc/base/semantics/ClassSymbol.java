package base.semantics;

public class ClassSymbol extends Symbol {
    MethodSymbolTable symbolTable = new MethodSymbolTable();
    ClassSymbol extension = null;
    String className;

    public ClassSymbol(String className) {
        super(Type.CLASS);
        this.className = className;
    }

    public ClassSymbol(String className, MethodSymbolTable symbolTable) {
        super(Type.CLASS);
        this.className = className;
        this.symbolTable = symbolTable;
    }

    public ClassSymbol(String className, MethodSymbolTable symbolTable, ClassSymbol extension) {
        super(Type.CLASS);
        this.className = className;
        this.symbolTable = symbolTable;
        this.extension = extension;
    }

    public void setExtension(ClassSymbol extension) {
        this.extension = extension;
    }

    public ClassSymbol getExtension() {
        return extension;
    }

    public MethodSymbolTable getSymbolTable() {
        return symbolTable;
    }

    public String getClassName() {
        return className;
    }

    public boolean derivesFrom(ClassSymbol targetParent) {
        if (className.equals(targetParent.getClassName())) return true;

        ClassSymbol parent = this.extension;
        while (parent != null) {
            String parentName = parent.getClassName();
            if (parentName.equals(targetParent.getClassName())) return true;
            parent = parent.extension;
        }
        return false;
    }
}
