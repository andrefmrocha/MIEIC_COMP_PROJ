package javamm.parser;

import javamm.SemanticsException;
import javamm.cfg.CFGNode;

import javamm.semantics.StackUsage;

import java.io.PrintWriter;
import java.util.HashMap;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * Node that represents FlowControl inside an if statement, either a Then or an Else
 */
public class FlowControlNode extends SimpleNode {
    private HashMap<Integer, Integer> requiredPops = new HashMap<>();

    public FlowControlNode(int i) {
        super(i);
    }

    public FlowControlNode(Javamm p, int i) {
        super(p, i);
    }

    public TreeSet<String> evaluate(Javamm parser) {
        final TreeSet<String> initializedVars = new TreeSet<String>(); // collect variables that were initialized
        for (int i = 0; i < this.jjtGetNumChildren(); i++) {
            SimpleNode child = (SimpleNode) this.jjtGetChild(i);
            child.setTables(table, methodTable);
            if (child.id == JavammTreeConstants.JJTASSIGNVAR) {
                final String identifier = ((ASTIdentifier) child.jjtGetChild(0)).identifierName;
                if (table.checkSymbol(identifier) && !table.getSymbol(identifier).isInitialized())
                    initializedVars.add(identifier);
            } else if (child.id == JavammTreeConstants.JJTIF) // nested if
                initializedVars.addAll(((ASTIf) child).initializedVars); // collect variables inside the nested if

            // invalid statement found, such as '1;'
            if (!child.validStatement) {
                parser.semanticErrors.add(new SemanticsException("Not a statement: " + child.toString(), child));
                break;
            }

            child.eval(parser); // recursive call
        }

        // initialized vars must be set to false, so that FlowControlNodes do not affect each other
        // if a variable is truly initialized in all FlowControlNodes of the If statement, the parent ASTIf shall
        // verify this and update them accordingly
        for (String str : initializedVars) {
            this.table.getSymbol(str).setInitialized(false);
        }

        return initializedVars;
    }

    public void write(PrintWriter writer) {
        for(int i = 0; i< this.jjtGetNumChildren(); i++) {
            SimpleNode node = (SimpleNode) this.jjtGetChild(i);
            node.write(writer);
            StackUsage.popStack(writer, requiredPops.get(i)); // pop any value left on the stack
        }
    }

    protected void calculateStackUsage(StackUsage stackUsage) {
        int stackUsageBefore = stackUsage.getStackUsage();
        for (int i = 0; i < this.jjtGetNumChildren(); i++) {
            SimpleNode child = (SimpleNode) this.jjtGetChild(i);
            child.calculateStackUsage(stackUsage);
            requiredPops.put(i, stackUsage.getStackUsage() - stackUsageBefore); // check if any value was left in stack
            stackUsage.set(stackUsageBefore); // reset stackUsage value to previous one
        }
    }

    @Override
    public List<CFGNode> getNodes() {
        List<CFGNode> graph = new ArrayList<>();
        for(int i = 0; i < this.jjtGetNumChildren(); i++){
            List<CFGNode> nodes = ((SimpleNode) this.jjtGetChild(i)).getNodes();

            if(graph.size() != 0)
                graph.get(graph.size() - 1).addEdge(nodes.get(0)); // connect children between themselves

            graph.addAll(nodes);
        }
        return graph;
    }
}
