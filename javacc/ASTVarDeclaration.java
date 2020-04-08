import semantics.Symbol;
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
        } else throw new SemanticsException("Parameter has not a valid identifier");

        if(table.checkSymbol(name)) throw new SemanticsException("Parameter has been defined previously");

        Symbol.Type type;
        switch (typeNode.id) {
            case ParserTreeConstants.JJTINTARRAY:
                type = Symbol.Type.INT_ARRAY;
                break;
            case ParserTreeConstants.JJTINT:
                type = Symbol.Type.INT;
                break;
            case ParserTreeConstants.JJTBOOLEAN:
                type = Symbol.Type.BOOL;
                break;
            case ParserTreeConstants.JJTVOID:
                type = Symbol.Type.VOID;
                break;
            case ParserTreeConstants.JJTIDENTIFIER:
                type = Symbol.Type.OBJ;
                break;
            default:
                throw new SemanticsException("Error type in parameter");
        }

        Symbol parameterSym = new Symbol(type); //TODO: get parameter value
        table.addSymbol(name, parameterSym);
    }
}
