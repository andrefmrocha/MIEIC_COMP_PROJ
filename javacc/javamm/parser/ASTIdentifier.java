package javamm.parser;

import javamm.semantics.StackUsage;
import javamm.semantics.Symbol;

import java.io.PrintWriter;

/* Generated By:JJTree: Do not edit this line. ASTIdentifier.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_Javamm=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTIdentifier extends TypeNode {
  public String identifierName;

  public ASTIdentifier(int id) {
    super(id);
  }

  public ASTIdentifier(Javamm p, int id) {
    super(p, id);
  }

  public String toString() {
    return JavammTreeConstants.jjtNodeName[id] + "[" + identifierName + "]";
  }

  @Override
  public void write(PrintWriter writer) {
    Symbol s = this.table.getSymbol(identifierName);
    String loadInstr = Symbol.getJVMPrefix(s.getType()) + "load";
    int varNum = s.getStackPos();

    if(varNum == -1) {
      writer.println("  aload_0");
      String className = this.table.getClassName();
      String jvmType = s.getJVMType();
      writer.println("  getfield " + className + "/" + identifierName + " " + jvmType);
    } else {
      String separator = varNum > 3 ? " " : "_";
      writer.println("  " + loadInstr + separator + Integer.toString(varNum));
    }
  }

  @Override
  protected void calculateStackUsage(StackUsage stackUsage) {
    stackUsage.inc(1);
  }

}
/* JavaCC - OriginalChecksum=75abfe948da46b0c7b3a59b2cae3a666 (do not edit this line) */
