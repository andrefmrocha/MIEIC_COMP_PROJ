package javamm.parser;

import javamm.SemanticsException;
import javamm.semantics.ClassSymbol;
import javamm.semantics.MethodSymbolTable;
import javamm.semantics.Symbol;
import javamm.semantics.SymbolTable;

import java.io.PrintWriter;

/* Generated By:JJTree: Do not edit this line. ASTClass.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_Javamm=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTClass extends SimpleNode {
  public ClassSymbol classSymbol;

  public ASTClass(int id) {
    super(id);
  }

  public ASTClass(Javamm p, int id) {
    super(p, id);
  }

  @Override
  public void eval(Javamm parser) {
    final MethodSymbolTable newTable = new MethodSymbolTable(methodTable);
    String className = null;
    ClassSymbol extendedClass = null;

    for (int i = 0; i < this.jjtGetNumChildren(); i++) {
      SimpleNode child = (SimpleNode) this.jjtGetChild(i);

      switch (child.id) {
        case JavammTreeConstants.JJTVAR:
          child.setTables(table, newTable);
          child.eval(parser);
          break;
        case JavammTreeConstants.JJTEXTEND:
          child.setTables(table, newTable);
          child.eval(parser);
          extendedClass = ((ASTExtend) child).extendedClass;
          break;
        case JavammTreeConstants.JJTIDENTIFIER:
          ASTIdentifier temp = (ASTIdentifier) child;
          className = temp.identifierName;
          this.table.setClassName(className);
          break;
        case JavammTreeConstants.JJTMETHOD:
          child.setTables(new SymbolTable(table), newTable);
          child.eval(parser);
          break;
        default:
          parser.semanticErrors.add(new SemanticsException("Incorrect child node", child));
          return;
      }
    }

    classSymbol = new ClassSymbol(Symbol.Type.CLASS, className, newTable, extendedClass);
    table.putSymbol(className, classSymbol);

    for (int i = 0; i < this.jjtGetNumChildren(); i++) {
      SimpleNode child = (SimpleNode) this.jjtGetChild(i);
      if (child.id == JavammTreeConstants.JJTMETHOD) {
        ASTMethod method = (ASTMethod) child;
        boolean hasThis = child.checkForThis(this.table);
        if(hasThis)
          for(Symbol symbol: method.getParameters())
            symbol.setStackPos(symbol.getStackPos() + 1);
        method.processBody(parser, method.getParameters().size() + (hasThis ? 1 : 0));
      }
    }
  }


  @Override
  public void write(PrintWriter writer) {
    writer.println(".class public " + classSymbol.getClassName());
    writer.println(".super " + (classSymbol.getExtension() == null ? "java/lang/Object" : classSymbol.getExtension().getClassName()));
    writer.println();

    int i = 1;
    for(; i <this.jjtGetNumChildren();i++) {
      SimpleNode child = (SimpleNode) this.jjtGetChild(i);
      if (child.id == JavammTreeConstants.JJTVAR)
        child.write(writer);
      else
        break;
    }
    if(i != 1)
      writer.println();

    writer.println(".method public <init>()V\n" +
        "  aload_0\n" +
        "  invokenonvirtual java/lang/Object/<init>()V\n" +
        "  return\n" +
        ".end method\n");

    for (; i < this.jjtGetNumChildren(); i++) {
      SimpleNode child = (SimpleNode) this.jjtGetChild(i);
      if (child.id == JavammTreeConstants.JJTMETHOD)
        child.write(writer);
    }
  }
}
/* JavaCC - OriginalChecksum=f92e5600b4d1b54c8cf65f0fa9dc9373 (do not edit this line) */
