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

    public boolean visited = false;

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

    public void resetInAndOutDFS() {
        this.visited = true;
        this.in.clear();
        this.out.clear();
        for(CFGNode succ : edges) {
            if(succ.visited) continue; // already visited
            succ.resetInAndOutDFS(); // dfs
        }
    }

    public void resetVisitedDFS() {
        this.visited = false;
        for(CFGNode succ : edges) {
            if(!succ.visited) continue; // already reseted
            succ.resetVisitedDFS(); // dfs
        }
    }

    public boolean generateInAndOut() {
        this.visited = true;
        final Set<Symbol> prev_in = new HashSet<>(this.getIn());
        final Set<Symbol> prev_out = new HashSet<>(this.getOut());
        this.updateOutStep();
        this.updateInStep();

        boolean stabilized = this.getIn().equals(prev_in) && this.getOut().equals(prev_out);
        for (CFGNode succ : edges) {
            if (succ.visited) continue;
            stabilized &= succ.generateInAndOut();
        }
        return stabilized;
    }

    public void updateInStep() {
        this.in.clear();
        this.in.addAll(out);
        this.in.removeAll(definedVars);
        this.in.addAll(usedVars);
    }

    public void updateOutStep() {
        this.out.clear();
        for (CFGNode succ : edges)
            this.out.addAll(succ.getIn());
    }
}
