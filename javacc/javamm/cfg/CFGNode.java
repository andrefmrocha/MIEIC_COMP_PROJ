package javamm.cfg;

import java.util.*;

/**
 * Represents the node of a CFG graph, which stores the definitions and used variables
 * It will also further out the set corresponding to the Live-In and Live-out of the node
 */
public class CFGNode {
    private final List<CFGNode> edges;
    private final Set<CFGSymbol> usedVars;
    private final Set<CFGSymbol> definedVars;
    private final Set<CFGSymbol> in = new HashSet<>();
    private final Set<CFGSymbol> out = new HashSet<>();
    private boolean isIf = false;

    public boolean visited = false;

    public CFGNode(List<CFGNode> edges, List<CFGSymbol> usedVars, List<CFGSymbol> definedVars) {
        this.edges = edges;
        this.usedVars = new HashSet<>(usedVars);
        this.definedVars = new HashSet<>(definedVars);
    }

    public CFGNode(List<CFGSymbol> usedVars) {
        this(new ArrayList<>(), usedVars, new ArrayList<>());
    }

    public CFGNode(List<CFGSymbol> usedVars, List<CFGSymbol> definedVars) {
        this(new ArrayList<>(), usedVars, definedVars);
    }

    public CFGNode(List<CFGSymbol> symbols, boolean isIf) {
        this(symbols);
        this.isIf = isIf;
    }

    public List<CFGNode> getEdges() {
        return edges;
    }

    public void addEdge(CFGNode node) {
        this.edges.add(node);
    }

    public Set<CFGSymbol> getUsedVars() {
        return usedVars;
    }

    public Set<CFGSymbol> getDefinedVars() {
        return definedVars;
    }

    public Set<CFGSymbol> getIn() {
        return in;
    }

    public Set<CFGSymbol> getOut() {
        return out;
    }

    /**
     * Resets in and out sets of node, using a DFS
     */
    public void resetInAndOutDFS() {
        this.visited = true;
        this.in.clear();
        this.out.clear();
        for (CFGNode succ : edges) {
            if (succ.visited) continue; // already visited
            succ.resetInAndOutDFS(); // dfs
        }
    }

    /**
     * Generates Live-In and Live-Out sets of the dataflow analysis
     * @return if the sets have changed during this iteration
     */
    public boolean generateInAndOut() {
        final Set<CFGSymbol> prev_in = new HashSet<>(this.getIn());
        final Set<CFGSymbol> prev_out = new HashSet<>(this.getOut());
        this.updateOutStep();
        this.updateInStep();

        return this.getIn().equals(prev_in) && this.getOut().equals(prev_out);
    }

    /**
     * Executes the Live-In step of the dataflow analysis algorithm
     */
    public void updateInStep() {
        this.in.clear();
        this.in.addAll(out);
        this.in.removeAll(definedVars);
        this.in.addAll(usedVars);
    }

    /**
     * Executes the Live-Out step of the dataflow analysis algorithm
     */
    public void updateOutStep() {
        this.out.clear();
        for (CFGNode succ : edges)
            this.out.addAll(succ.getIn());
    }

    /**
     * Checks if the given node is a placeholder, meaning, it does not
     * have any relevant information and therefore can be removed.
     * @return if it is a placeholder
     */
    public boolean isPlaceholder() {
        return this.definedVars.isEmpty() && this.usedVars.isEmpty() && !this.isIf;
    }

    /**
     * Resets node's visited flag
     */
    public void resetVisited() {
        if (this.visited) {
            this.visited = false;
            edges.forEach(CFGNode::resetVisited);
        }
    }
}
