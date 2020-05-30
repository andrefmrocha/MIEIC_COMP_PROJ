package javamm.parser;

import javamm.SemanticsException;
import javamm.cfg.CFGNode;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class FlowControlNode extends SimpleNode {
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

    @Override
    public List<CFGNode> getNodes() {
        List<CFGNode> graph = new ArrayList<>();
        for(int i = 0; i < this.jjtGetNumChildren(); i++){
            List<CFGNode> nodes = ((SimpleNode) this.jjtGetChild(i)).getNodes();

            if(graph.size() != 0)
                graph.get(graph.size() - 1).addEdge(nodes.get(0));

            graph.addAll(nodes);
        }
        return graph;
    }
}
