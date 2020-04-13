import semantics.Symbol;
import semantics.Symbol.Type;
import semantics.SymbolTable;

public class ASTVarDeclaration extends SimpleNode {
    public ASTVarDeclaration(int i) {
        super(i);
    }

    public ASTVarDeclaration(Parser p, int i) {
        super(p, i);
    }

    public ASTVarDeclaration(int i, Node type, Node identifier, SymbolTable table) {
        super(i);
        this.jjtAddChild(type,0);
        this.jjtAddChild(identifier,1);
        this.setTable(table);
    }

    @Override
    public void eval() throws SemanticsException {
        if(this.jjtGetNumChildren() != 2) throw new SemanticsException("Variable declaration must have type and identifier");

        SimpleNode typeNode = (SimpleNode) this.jjtGetChild(0);
        SimpleNode identifier = (SimpleNode) this.jjtGetChild(1);

        String name;
        if(identifier instanceof ASTIdentifier) {
            ASTIdentifier temp = (ASTIdentifier) identifier;
            name = temp.identifierName;
        } else throw new SemanticsException("Variable has not a valid identifier");

        if(table.checkSymbol(name)) throw new SemanticsException("Parameter has been defined previously");

        Type type = getType(typeNode.id);

        Symbol parameterSym = new Symbol(type); //TODO: get parameter value
        table.putSymbol(name, parameterSym);
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
