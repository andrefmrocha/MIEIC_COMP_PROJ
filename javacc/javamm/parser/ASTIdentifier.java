package javamm.parser;

import javamm.cfg.CFGSymbol;
import javamm.semantics.StackUsage;
import javamm.semantics.Symbol;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/* Generated By:JJTree: Do not edit this line. ASTIdentifier.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_Javamm=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTIdentifier extends TypeNode {
  public String identifierName;
  public int value = -1;

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
  public void eval(Javamm parser) {
    if(this.table != null) {
      Symbol s = this.table.getSymbol(identifierName);
      if (s != null) value = s.getValue();
    }
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
    } else if(ASTProgram.optimize && !s.hasChanged() && value != -1) {
      ASTNumeric.writeNumericInstruction(writer,value);
    } else {
      String separator = varNum > 3 ? " " : "_";
      writer.println("  " + loadInstr + separator + Integer.toString(varNum));
    }
  }

  @Override
  protected void calculateStackUsage(StackUsage stackUsage) {
    stackUsage.inc(1);
  }

  @Override
  public List<CFGSymbol> getSymbols() {
    final List<CFGSymbol> symbol = new ArrayList<>();
    if(table.checkSymbol(identifierName)){
      Symbol s = table.getSymbol(identifierName);
      if(s.getType() != Symbol.Type.CLASS && s.getStackPos() != -1){
        symbol.add(new CFGSymbol(identifierName, s));
      } else {
        System.out.println("Won't add variable with name " + identifierName + " with type " + s.getType() + " and pos " + s.getStackPos() + " in line " + getLine());
      }
    }

    return symbol;
  }
}
/* JavaCC - OriginalChecksum=75abfe948da46b0c7b3a59b2cae3a666 (do not edit this line) */
