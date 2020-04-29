package javamm.parser;

import javamm.semantics.Symbol;

import java.util.TreeSet;

public class FlowControlNode extends SimpleNode {
    public FlowControlNode (int i) {
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
            if(child.id == JavammTreeConstants.JJTASSIGNVAR) {
                final String identifier = ((ASTIdentifier) child.jjtGetChild(0)).identifierName;
                if(table.checkSymbol(identifier) && !table.getSymbol(identifier).isInitialized())
                    initializedVars.add(identifier);
            }
            child.eval(parser);
        }

        for (String str : initializedVars) {
            this.table.getSymbol(str).setInitialized(false);
        }

        return initializedVars;
    }
}
