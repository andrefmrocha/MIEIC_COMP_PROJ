package javamm.parser;

import javamm.semantics.Symbol;

import java.io.PrintWriter;

public abstract class BoolBiOperatorNode extends BinaryOperatorNode{

  public BoolBiOperatorNode(int i, Symbol.Type type, Symbol.Type op) {
    super(i, type, op);
  }

  public BoolBiOperatorNode(Javamm p, int i, Symbol.Type type, Symbol.Type op) {
    super(p, i, type, op);
  }

  public abstract void write(PrintWriter writer, String labelFalse);
}
