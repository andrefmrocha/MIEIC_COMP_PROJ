package base;

import base.semantics.Symbol;

/* Generated By:JJTree: Do not edit this line. ASTLength.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTLength extends TypeNode {
  public ASTLength(int id) {
    super(id);
    type = Symbol.Type.INT;
  }

  public ASTLength(Parser p, int id) {
    super(p, id);
    type = Symbol.Type.INT;
  }

  @Override
  public void eval() throws SemanticsException {
    if (this.jjtGetNumChildren() != 1) throw new SemanticsException("Wrong number of children found");


    final SimpleNode var = (SimpleNode) this.jjtGetChild(0);
    switch (var.id){
      case ParserTreeConstants.JJTIDENTIFIER:
        final ASTIdentifier identifier = (ASTIdentifier) var;
        if (!this.table.checkSymbol(identifier.identifierName))
          throw new SemanticsException("No variable " + identifier.identifierName + " was found");

        final Symbol symbol = table.getSymbol(identifier.identifierName);
        if(symbol.getType() != Symbol.Type.INT_ARRAY) //TODO this condition is triggered in 'testLife'
          throw new SemanticsException("Variable is not an int array");
        break;

      case ParserTreeConstants.JJTMETHODCALL:
        final ASTMethodCall call = (ASTMethodCall) var;
        call.eval();

        if(call.type != Symbol.Type.INT_ARRAY)
          throw new SemanticsException("Method call not does return int array");

        break;

      default:
        throw new SemanticsException("No valid array provided for length call");

    }


  }
}
/* JavaCC - OriginalChecksum=d20841b9a326e3b2ada1a7a0c3a4a9a1 (do not edit this line) */
