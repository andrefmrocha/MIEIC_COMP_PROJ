package javamm.parser;

import javamm.SemanticsException;
import javamm.semantics.ClassSymbol;
import javamm.semantics.Symbol;
import javamm.semantics.Symbol.Type;
import javamm.semantics.SymbolTable;


public class VarNode extends SimpleNode {
    private final boolean isInitialized;

    public VarNode(int i, boolean isInitialized) {
        super(i);
        this.isInitialized = isInitialized;
    }

    public VarNode(Javamm p, int i, boolean isInitialized) {
        super(p, i);
        this.isInitialized = isInitialized;
    }

    public VarNode(int i, Node type, Node identifier, SymbolTable table, boolean isInitialized) {
        super(i);
        this.jjtAddChild(type, 0);
        this.jjtAddChild(identifier, 1);
        this.setTables(table, methodTable);
        this.isInitialized = isInitialized;
    }

    @Override
    public void eval(Javamm parser) {
        if (this.jjtGetNumChildren() != 2){
            parser.semanticErrors.add(new SemanticsException("Variable declaration must have type and identifier", this));
            return;
        }

        SimpleNode typeNode = (SimpleNode) this.jjtGetChild(0);
        SimpleNode identifier = (SimpleNode) this.jjtGetChild(1);


        if (identifier.id != JavammTreeConstants.JJTIDENTIFIER){
            parser.semanticErrors.add(new SemanticsException("Variable has not a valid identifier", identifier));
            return;
        }

        ASTIdentifier temp = (ASTIdentifier) identifier;
        String name = temp.identifierName;

        if (table.checkSymbolWithinScope(name)){
            parser.semanticErrors.add(new SemanticsException("Variable " + name + " has been defined previously", temp));
            return;
        }

        Type type = getType(typeNode, table, parser);

        Symbol varSym;
        if (type != Type.CLASS)
            varSym = new Symbol(type, "", isInitialized);
        else {
            ClassSymbol symbol = (ClassSymbol) table.getSymbol(((ASTIdentifier) typeNode).identifierName);
            varSym = new ClassSymbol(Type.OBJ,symbol.getClassName(), symbol.getSymbolTable(), symbol.getExtension());
        }
        table.putSymbol(name, varSym);
    }

    public static Type getType(SimpleNode node, SymbolTable table, Javamm parser) {
        switch (node.id) {
            case JavammTreeConstants.JJTINTARRAY:
                return Type.INT_ARRAY;
            case JavammTreeConstants.JJTINT:
                return Type.INT;
            case JavammTreeConstants.JJTBOOLEAN:
                return Type.BOOL;
            case JavammTreeConstants.JJTVOID:
                return Type.VOID;
            case JavammTreeConstants.JJTIDENTIFIER:
                final ASTIdentifier identifier = (ASTIdentifier) node;
                if (!table.checkSymbol(identifier.identifierName)) {
                    parser.semanticErrors.add(new SemanticsException("Could not find " + identifier.identifierName, node));
                    return Type.VOID;
                }
                return table.getSymbol(identifier.identifierName).getType();
            default:
                parser.semanticErrors.add(new SemanticsException("Found invalid type", node));
                return Type.VOID;
        }
    }
}
