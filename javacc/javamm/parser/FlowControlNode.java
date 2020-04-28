package javamm.parser;

import javamm.semantics.Symbol;

import java.util.ArrayList;
import java.util.List;

public class FlowControlNode extends SimpleNode {
    public FlowControlNode (int i) {
        super(i);
    }

    public FlowControlNode(Javamm p, int i) {
        super(p, i);
    }

    public List<String> evaluate(Javamm parser) {
        final List<String> initializedVars = new ArrayList<>();
        for (int i = 1; i < this.jjtGetNumChildren(); i++) {
            SimpleNode child = (SimpleNode) this.jjtGetChild(i);
            child.setTables(table, methodTable);
            if(child.id == JavammTreeConstants.JJTASSIGNVAR) {
                final String identifier = ((ASTIdentifier) child.jjtGetChild(0)).identifierName;
                if(table.checkSymbol(identifier) && !table.getSymbol(identifier).isInitialized())
                    initializedVars.add(identifier);
            }
            child.eval(parser);
        }

        return initializedVars;
    }
}
