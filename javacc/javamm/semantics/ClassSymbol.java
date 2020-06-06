package javamm.semantics;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Symbol used to store a given class. It stores information regarding its methods as well
 * as its constructors
 */
public class ClassSymbol extends Symbol {
    public static final String init = "<init>";
    MethodSymbolTable methods = new MethodSymbolTable();
    MethodSymbolTable constructors = new MethodSymbolTable();
    ClassSymbol extension = null;
    String className;

    public ClassSymbol(String className) {
        super(Type.CLASS, true);
        constructors.putSymbol(new MethodIdentifier(init, new ArrayList<>()), new MethodSymbol(this, new ArrayList<>()));
        this.className = className;
    }

    public ClassSymbol(String className, List<Symbol> constructorParams) {
        super(Type.CLASS, true);
        constructors.putSymbol(new MethodIdentifier(init, constructorParams), new MethodSymbol(this, constructorParams));
        this.className = className;
    }

    public ClassSymbol(Type type, String className, MethodSymbolTable methods) {
        super(type, true);
        constructors.putSymbol(new MethodIdentifier(init, new ArrayList<>()), new MethodSymbol(this, new ArrayList<>()));
        this.className = className;
        this.methods = methods;
    }

    public ClassSymbol(Type type, String className, MethodSymbolTable methods, ClassSymbol extension) {
        super(type, true);
        constructors.putSymbol(new MethodIdentifier(init, new ArrayList<>()), new MethodSymbol(this, new ArrayList<>()));
        this.className = className;
        this.methods = methods;
        this.extension = extension;
    }

    public ClassSymbol(Type type, String className, MethodSymbolTable methods, ClassSymbol extension, int stackPos) {
        super(type, true, stackPos);
        constructors.putSymbol(new MethodIdentifier(init, new ArrayList<>()), new MethodSymbol(this, new ArrayList<>()));
        this.className = className;
        this.methods = methods;
        this.extension = extension;
    }

    public void setExtension(ClassSymbol extension) {
        this.extension = extension;
    }

    public ClassSymbol getExtension() {
        return extension;
    }

    public MethodSymbolTable getMethods() {
        return methods;
    }

    public MethodSymbolTable getConstructors() {
        return constructors;
    }

    public String getClassName() {
        return className;
    }

    /**
     * Checks if a class symbol derives from the target parent
     * @param targetParent - the target parent's class symbol
     * @return if it is derives from the given symbol
     */
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

    @Override
    public String getJVMType() {
        return "L" + className + ";";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassSymbol that = (ClassSymbol) o;
        return derivesFrom(that);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Type.CLASS);
    }

    @Override
    public String toString() {
        return "ClassSymbol{" +
                "methods=" + methods +
                ", constructors=" + constructors +
                ", extension=" + extension +
                ", className='" + className + '\'' +
                '}';
    }
}
