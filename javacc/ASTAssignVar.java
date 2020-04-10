import semantics.Symbol;
import semantics.Symbol.Type;

/* Generated By:JJTree: Do not edit this line. ASTAssignVar.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTAssignVar extends TypeNode {
  public String varIdentifier;
  public ASTAssignVar(int id) {
    super(id);
  }

  public ASTAssignVar(Parser p, int id) {
    super(p, id);
  }

  @Override
  public void eval() throws SemanticsException {
    if(this.jjtGetNumChildren() != 2) throw new SemanticsException("Variable assignment must have two operators");

    SimpleNode identifier = (SimpleNode) this.jjtGetChild(0);

    String name;
    if(identifier.id == ParserTreeConstants.JJTIDENTIFIER) {
      ASTIdentifier temp = (ASTIdentifier) identifier;
      name = temp.identifierName;
    } else throw new SemanticsException("Variable has not a valid identifier");

    Type type;
    if(this.table.checkSymbol(name)) {
      Symbol symbol = this.table.getSymbol(name);
      type = symbol.getType();
    } else return;//throw new SemanticsException("Variable " + name + " has not been initialized");

    SimpleNode expression = (SimpleNode) this.jjtGetChild(1);
    this.evaluateChild(expression, type);

  }
}
/* JavaCC - OriginalChecksum=661756e145ed220ec46575b3a8adecd3 (do not edit this line) */
