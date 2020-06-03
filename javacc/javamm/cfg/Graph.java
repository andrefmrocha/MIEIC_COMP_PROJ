package javamm.cfg;

import java.util.List;

public class Graph {
    private final List<CFGNode> nodes;
    private final CFGNode root;

    public Graph(List<CFGNode> nodes) {
        this.nodes = nodes;
        this.root = nodes.size() > 0 ? nodes.get(0) : null;
        generateInAndOut();
    }

    public void generateInAndOut(){
        if(root != null) {
            resetInAndOut();
            while (!root.generateInAndOut()) {
                resetVisited();
            }
        }
    }

    private void resetInAndOut() {
        root.resetInAndOutDFS();
        resetVisited();
    }

    private void resetVisited() {
        root.resetVisitedDFS();
    }
}
