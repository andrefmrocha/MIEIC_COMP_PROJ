import semantics.Symbol;

/* Generated By:JJTree: Do not edit this line. ASTWhile.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTWhile extends TypeNode {
  public ASTWhile(int id) {
    super(id);
  }

  public ASTWhile(Parser p, int id) {
    super(p, id);
  }

  @Override
  public void eval() throws SemanticsException {
    if(this.jjtGetNumChildren() < 1) throw new SemanticsException("While statement must have at least the condition expression");

    SimpleNode condition = (SimpleNode) this.jjtGetChild(0);
    this.evaluateChild(condition, Symbol.Type.BOOL);

    for(int i = 1; i < this.jjtGetNumChildren(); i++) {
      SimpleNode child = (SimpleNode) this.jjtGetChild(i);
      child.setTable(table);
      child.eval();
    }
  }
}
/* JavaCC - OriginalChecksum=5f4455af0142b2fe9a424f1a313045fd (do not edit this line) */
