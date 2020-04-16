package base;

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

        Type type = getType(typeNode.id);

        Symbol varSym = new Symbol(type);
        table.putSymbol(name, varSym);
    }

    public static Symbol.Type getType(int id) throws SemanticsException {
        switch (id) {
            case ParserTreeConstants.JJTINTARRAY:
                return Symbol.Type.INT_ARRAY;
            case ParserTreeConstants.JJTINT:
                return Symbol.Type.INT;
            case ParserTreeConstants.JJTBOOLEAN:
                return Symbol.Type.BOOL;
            case ParserTreeConstants.JJTVOID:
                return Symbol.Type.VOID;
            case ParserTreeConstants.JJTIDENTIFIER:
                return Symbol.Type.OBJ;
            default:
                throw new SemanticsException("Found invalid type");
        }
    }
}
