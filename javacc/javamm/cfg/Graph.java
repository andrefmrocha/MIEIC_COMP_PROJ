package javamm.cfg;

import javamm.semantics.Symbol;

import java.util.*;

public class Graph {
    private final List<CFGNode> nodes;
    private CFGNode root;

    public Graph(List<CFGNode> nodes) {
        this.nodes = this.removePlaceHolders(nodes);
        generateInAndOut();
    }

    private List<CFGNode> removePlaceHolders(List<CFGNode> nodes) {
        for (CFGNode node : nodes) {
            if (!node.isPlaceholder()) {
                this.root = node;
                break;
            }
        }

        final List<CFGNode> finalNodes = new ArrayList<>();


        for (CFGNode node : nodes) {
            if (node.isPlaceholder())
                continue;

            finalNodes.add(node);
            for (CFGNode edge : new ArrayList<>(node.getEdges())) {
                if (edge.isPlaceholder()) {
                    node.getEdges().remove(edge);
                    while (!edge.getEdges().isEmpty()) {
                        edge = edge.getEdges().get(0);
                        if (!edge.isPlaceholder()) {
                            node.getEdges().add(edge);
                            break;
                        }
                    }
                }
            }
        }
        return finalNodes;
    }

    public void generateInAndOut() {
        if (root != null) {
            resetInAndOut();
            boolean stabilized = false;
            while (!stabilized){
                stabilized = true;
                for(CFGNode node: nodes)
                    stabilized &= node.generateInAndOut();
            }
            
            resetVisited();
        }
    }

    private void resetInAndOut() {
        root.resetInAndOutDFS();
        resetVisited();
    }

    private void resetVisited() {
        for (CFGNode node: nodes)
            node.visited = false;
    }

    public void generateStackPos(int maxSize, int numParams) throws NoSuchElementException {
        if(root == null)
            return;
        final PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(maxSize);

        for (int i = numParams; i < maxSize; i++) {
            priorityQueue.offer(i);
        }

        root.generateStackPos(priorityQueue, new HashSet<>(), numParams);
        resetVisited();
    }
}
