package javamm.parser;

import javamm.semantics.StackUsage;
import javamm.semantics.Symbol;

import java.io.PrintWriter;

public abstract class BooleanBinaryOperatorNode extends BinaryOperatorNode{

  public BooleanBinaryOperatorNode(int i, Symbol.Type type, Symbol.Type op) {
    super(i, type, op);
  }

  public BooleanBinaryOperatorNode(Javamm p, int i, Symbol.Type type, Symbol.Type op) {
    super(p, i, type, op);
  }

  public abstract void write(PrintWriter writer, String labelFalse);

  public abstract void calculateParamsStackUsage(StackUsage stackUsage);
}
