import semantics.Symbol.Type;

/* Generated By:JJTree: Do not edit this line. ASTNew.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTNew extends TypeNode {
  public String classIdentifier;
  public ASTNew(int id) {
    super(id);
    type = Type.OBJ;
  }

  public ASTNew(Parser p, int id) {
    super(p, id);
    type = Type.OBJ;
  }

}
/* JavaCC - OriginalChecksum=c6d588009442d8c81f835326710afcd3 (do not edit this line) */
