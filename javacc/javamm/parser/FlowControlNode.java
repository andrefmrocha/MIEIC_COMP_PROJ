package javamm.parser;

import javamm.SemanticsException;
import javamm.semantics.StackUsage;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.TreeSet;

public class FlowControlNode extends SimpleNode {
    private HashMap<Integer, Integer> requiredPops = new HashMap<>();

    public FlowControlNode(int i) {
        super(i);
    }

    public FlowControlNode(Javamm p, int i) {
        super(p, i);
    }

    public TreeSet<String> evaluate(Javamm parser) {
        final TreeSet<String> initializedVars = new TreeSet<String>();
        for (int i = 0; i < this.jjtGetNumChildren(); i++) {
            SimpleNode child = (SimpleNode) this.jjtGetChild(i);
            child.setTables(table, methodTable);
            if (child.id == JavammTreeConstants.JJTASSIGNVAR) {
                final String identifier = ((ASTIdentifier) child.jjtGetChild(0)).identifierName;
                if (table.checkSymbol(identifier) && !table.getSymbol(identifier).isInitialized())
                    initializedVars.add(identifier);
            } else if (child.id == JavammTreeConstants.JJTIF)
                initializedVars.addAll(((ASTIf) child).initializedVars);

            if (!child.validStatement) {
                parser.semanticErrors.add(new SemanticsException("Not a statement: " + child.toString(), child));
                break;
            }

            child.eval(parser);
        }

        for (String str : initializedVars) {
            this.table.getSymbol(str).setInitialized(false);
        }

        return initializedVars;
    }

    public void write(PrintWriter writer) {
        for(int i = 0; i< this.jjtGetNumChildren(); i++) {
            SimpleNode node = (SimpleNode) this.jjtGetChild(i);
            node.write(writer);
            StackUsage.popStack(writer, requiredPops.get(i));
        }
    }

    protected void calculateStackUsage(StackUsage stackUsage) {
        int stackUsageBefore = stackUsage.getStackUsage();
        for(int i = 0; i < this.jjtGetNumChildren(); i++) {
            SimpleNode child = (SimpleNode) this.jjtGetChild(i);
            child.calculateStackUsage(stackUsage);
            requiredPops.put(i, stackUsage.getStackUsage() - stackUsageBefore);
            stackUsage.set(stackUsageBefore);
        }
    }
}
