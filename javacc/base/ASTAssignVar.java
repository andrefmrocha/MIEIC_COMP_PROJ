package base;

import base.semantics.ClassSymbol;
import base.semantics.Symbol;
import base.semantics.Symbol.Type;

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
    if(identifier.id != ParserTreeConstants.JJTIDENTIFIER)
      throw new SemanticsException("Variable has not a valid identifier");

    ASTIdentifier temp = (ASTIdentifier) identifier;
    name = temp.identifierName;

    Type type;
    if(!this.table.checkSymbol(name))
      throw new SemanticsException("Variable " + name + " does not exist in line" + getLine());

    Symbol symbol = this.table.getSymbol(name);
    type = symbol.getType();

    SimpleNode expression = (SimpleNode) this.jjtGetChild(1);
    this.evaluateChild(expression, new Symbol(type));

    symbol.setInitialized();
  }
}
/* JavaCC - OriginalChecksum=661756e145ed220ec46575b3a8adecd3 (do not edit this line) */
