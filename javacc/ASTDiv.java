import semantics.Symbol.Type;

/* Generated By:JJTree: Do not edit this line. ASTDiv.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTDiv extends BinaryOperatorNode {
  public ASTDiv(int id) {
    super(id,Type.INT,Type.INT);
  }

  public ASTDiv(Parser p, int id) {
    super(p, id,Type.INT,Type.INT);
  }

  @Override
  public Type[] getSupportedTypes() {
    return new Type[]{Type.INT};
  }
}
/* JavaCC - OriginalChecksum=a6ae99679f76890aff99ebcee897988e (do not edit this line) */
