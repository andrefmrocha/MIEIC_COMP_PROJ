package javamm.parser;

import javamm.SemanticsException;
import javamm.cfg.CFGNode;
import javamm.semantics.MethodSymbolTable;
import javamm.semantics.StackUsage;
import javamm.semantics.Symbol;
import javamm.semantics.SymbolTable;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* Generated By:JJTree: Do not edit this line. SimpleNode.java Version 7.0 */
/* JavaCCOptions:MULTI=false,NODE_USES_Javamm=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class SimpleNode implements Node {

  protected Node parent;
  protected Node[] children;
  public int id;
  protected Object value;
  protected Javamm parser;
  protected SymbolTable table;
  protected MethodSymbolTable methodTable;
  protected boolean validStatement = false;
  private int line = 0;

  public void setLine( int line ) { this.line = line ; }
  public int getLine() { return line ; }

  public SimpleNode(int i) {
    id = i;
  }

  public SimpleNode(Javamm p, int i) {
    this(i);
    parser = p;
  }

  public void setTables(SymbolTable table, MethodSymbolTable methodTable){
    this.table = table;
    this.methodTable = methodTable;
  }

  public void jjtOpen() {
  }

  public void eval(Javamm parser)  {
    for(int i = 0; i< this.jjtGetNumChildren(); i++) {
      SimpleNode node = (SimpleNode) this.jjtGetChild(i);
      node.setTables(this.table, this.methodTable);
      node.eval(parser);
    }
  }

  public void write(PrintWriter writer) {
    for(int i = 0; i< this.jjtGetNumChildren(); i++) {
      SimpleNode node = (SimpleNode) this.jjtGetChild(i);
      node.write(writer);
    }
  }

  public void printTable()  {
    for(int i = 0; i< this.jjtGetNumChildren(); i++) {
      SimpleNode node = (SimpleNode) this.jjtGetChild(i);
      node.printTable();
    }
  }

  public void jjtClose() {
  }

  int process(){
    return 0;
  }

  public void jjtSetParent(Node n) { parent = n; }
  public Node jjtGetParent() { return parent; }

  public void jjtAddChild(Node n, int i) {
    if (children == null) {
      children = new Node[i + 1];
    } else if (i >= children.length) {
      Node c[] = new Node[i + 1];
      System.arraycopy(children, 0, c, 0, children.length);
      children = c;
    }
    children[i] = n;
  }

  public Node jjtGetChild(int i) {
    return children[i];
  }

  public int jjtGetNumChildren() {
    return (children == null) ? 0 : children.length;
  }

  public void jjtSetValue(Object value) { this.value = value; }
  public Object jjtGetValue() { return value; }

  /* You can override these two methods in subclasses of SimpleNode to
     customize the way the node appears when the tree is dumped.  If
     your output uses more than one line you should override
     toString(String), otherwise overriding toString() is probably all
     you need to do. */

  public String toString() {
    return JavammTreeConstants.jjtNodeName[id];
  }
  public String toString(String prefix) { return prefix + toString(); }



  /* Override this method if you want to customize how the node dumps
     out its children. */

  public void dump(String prefix) {
    System.out.println(toString(prefix));

    if (children != null) {
      for (int i = 0; i < children.length; ++i) {
        SimpleNode n = (SimpleNode)children[i];
        if (n != null) {
          n.dump("  " + prefix + " ");
        }
      }
    }
  }

  public int getId() {
    return id;
  }

  protected void calculateStackUsage(StackUsage stackUsage) {
    for(int i = 0; i < this.jjtGetNumChildren(); i++) {
      SimpleNode child = (SimpleNode) this.jjtGetChild(i);
      child.calculateStackUsage(stackUsage);
    }
  }

  public List<CFGNode> getNodes() {
    return Collections.singletonList(new CFGNode(getSymbols()));
  }

  public List<Symbol> getSymbols() {
    List<Symbol> nodes = new ArrayList<>();
    for(int i = 0; i < this.jjtGetNumChildren(); i++){
      final SimpleNode node = ((SimpleNode) this.jjtGetChild(i));
      node.setTables(table, methodTable);
      nodes.addAll(node.getSymbols());
    }
    return nodes;
  }
}

/* JavaCC - OriginalChecksum=221dbcb2c3eee918f7ff2d2eecd7686d (do not edit this line) */
