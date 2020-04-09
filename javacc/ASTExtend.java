/* Generated By:JJTree: Do not edit this line. ASTExtend.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTExtend extends SimpleNode {
  public ASTExtend(int id) {
    super(id);
  }

  public ASTExtend(Parser p, int id) {
    super(p, id);
  }

  public void eval() throws SemanticsException {

    if(this.jjtGetNumChildren() != 1)
      throw new SemanticsException("Lacks the number of required children!");

    SimpleNode child = (SimpleNode) this.jjtGetChild(0);

    String name;
    if(child instanceof ASTIdentifier) {
      ASTIdentifier temp = (ASTIdentifier) child;
      name = temp.identifierName;
    } else throw new SemanticsException("Parameter has not a valid identifier");

    if(this.table.getSymbol(name)) throw new SemanticsException("Parameter has been defined previously");

  }

}
/* JavaCC - OriginalChecksum=e49346eb773d71c8a3e9bf74b151c1de (do not edit this line) */
