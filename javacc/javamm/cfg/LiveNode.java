package javamm.cfg;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Live analysis node, used to build the interference graph
 */
public class LiveNode {
    private final CFGSymbol symbol;
    private final Set<LiveNode> edges = new HashSet<>();
    private Set<LiveNode> coloringEdges = null;
    private boolean visited = false;
    private int stackPos = -1;

    public LiveNode(CFGSymbol symbol) {
        this.symbol = symbol;
    }

    public void addEdge(LiveNode node){
        edges.add(node);
    }

    public void setColoringEdges(){
        coloringEdges = new HashSet<>(edges);
    }

    public CFGSymbol getSymbol() {
        return symbol;
    }

    public Set<LiveNode> getEdges() {
        return edges;
    }

    public Set<LiveNode> getColoringEdges() {
        return coloringEdges;
    }

    public void reset(){
        setColoringEdges();
        visited = false;
    }

    /**
     * Visits the given node, removing the edge from the other nodes.
     */
    public void visitNode(){
        visited = true;
        coloringEdges.forEach(node -> node.getColoringEdges().remove(this));
    }

    public boolean isVisited() {
        return visited;
    }

    public int getStackPos() {
        return stackPos;
    }

    public void setStackPos(int stackPos) {
        this.stackPos = stackPos;
        symbol.getSymbol().setStackPos(stackPos);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LiveNode liveNode = (LiveNode) o;
        return Objects.equals(symbol, liveNode.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol);
    }

    @Override
    public String toString() {
        return "LiveNode{" +
                "symbol=" + symbol.getIdentifier() +
                ", edges=" + Arrays.toString(edges.stream().map(node -> node.getSymbol().getIdentifier()).toArray()) +
                '}';
    }
}
