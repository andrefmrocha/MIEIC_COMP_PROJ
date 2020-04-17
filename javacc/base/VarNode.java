package base;

import base.semantics.ClassSymbol;
import base.semantics.Symbol;
import base.semantics.Symbol.Type;
import base.semantics.SymbolTable;

public class VarNode extends SimpleNode {
    public VarNode(int i) {
        super(i);
    }

    public VarNode(Parser p, int i) {
        super(p, i);
    }

    public VarNode(int i, Node type, Node identifier, SymbolTable table) {
        super(i);
        this.jjtAddChild(type,0);
        this.jjtAddChild(identifier,1);
        this.setTables(table, methodTable);
    }

    @Override
    public void eval() throws SemanticsException {
        if(this.jjtGetNumChildren() != 2) throw new SemanticsException("Variable declaration must have type and identifier");

        SimpleNode typeNode = (SimpleNode) this.jjtGetChild(0);
        SimpleNode identifier = (SimpleNode) this.jjtGetChild(1);

        String name;
        if(identifier.id != ParserTreeConstants.JJTIDENTIFIER)
            throw new SemanticsException(" Variable has not a valid identifier");
        else {
            ASTIdentifier temp = (ASTIdentifier) identifier;
            name = temp.identifierName;
        }

        if(table.checkSymbolWithinScope(name)) throw new SemanticsException("Variable " + name + " has been defined previously");

        Type type = getType(typeNode, table);

        Symbol varSym;
        if(type != Type.CLASS)
             varSym = new Symbol(type);
        else {
            ClassSymbol symbol = (ClassSymbol) table.getSymbol(((ASTIdentifier) typeNode).identifierName);
            varSym = new ClassSymbol(symbol.getSymbolTable());
        }
        table.putSymbol(name, varSym);
    }

    public static Type getType(SimpleNode node, SymbolTable table) throws SemanticsException {
        switch (node.id) {
            case ParserTreeConstants.JJTINTARRAY:
                return Type.INT_ARRAY;
            case ParserTreeConstants.JJTINT:
                return Type.INT;
            case ParserTreeConstants.JJTBOOLEAN:
                return Type.BOOL;
            case ParserTreeConstants.JJTVOID:
                return Type.VOID;
            case ParserTreeConstants.JJTIDENTIFIER:
                final ASTIdentifier identifier = (ASTIdentifier) node;
                if(!table.checkSymbol(identifier.identifierName))
                    throw new SemanticsException("Could not find " + identifier.identifierName);
                return table.getSymbol(identifier.identifierName).getType();
            default:
                throw new SemanticsException("Found invalid type");
        }
    }
}
