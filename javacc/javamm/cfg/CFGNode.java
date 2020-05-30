package javamm.cfg;

import javamm.semantics.Symbol;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CFGNode {
    private final List<CFGNode> edges;
    private final Set<Symbol> usedVars;
    private final Set<Symbol> definedVars;
    private final Set<Symbol> in = new HashSet<>();
    private final Set<Symbol> out = new HashSet<>();

    public CFGNode(List<CFGNode> edges, List<Symbol> usedVars, List<Symbol> definedVars) {
        this.edges = edges;
        this.usedVars = new HashSet<>(usedVars);
        this.definedVars = new HashSet<>(definedVars);
    }

    public CFGNode(List<Symbol> usedVars) {
        this(new ArrayList<>(), usedVars, new ArrayList<>());
    }

    public CFGNode(List<Symbol> usedVars, List<Symbol> definedVars) {
        this(new ArrayList<>(), usedVars, definedVars);
    }

    public List<CFGNode> getEdges() {
        return edges;
    }

    public void addEdge(CFGNode node){
        this.edges.add(node);
    }

    public Set<Symbol> getUsedVars() {
        return usedVars;
    }

    public Set<Symbol> getDefinedVars() {
        return definedVars;
    }

    public Set<Symbol> getIn() {
        return in;
    }

    public Set<Symbol> getOut() {
        return out;
    }
}
