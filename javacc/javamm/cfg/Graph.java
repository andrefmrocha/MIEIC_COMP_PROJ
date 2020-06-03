package javamm.cfg;

import javamm.semantics.Symbol;

import java.util.*;

public class Graph {
    private final List<CFGNode> nodes;
    private final CFGNode root;

    public Graph(List<CFGNode> nodes) {
        this.nodes = nodes;
        this.root = nodes.size() > 0 ? nodes.get(0) : null;
        generateInAndOut();
    }

    public void generateInAndOut() {
        if (root != null) {
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

    public void generateStackPos(int maxSize, int numParams) throws NoSuchElementException {
        final PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(maxSize);

        for (int i = numParams; i < maxSize; i++) {
            priorityQueue.offer(i);
        }

        for (CFGNode node : nodes) {
            System.out.println("In: " + Arrays.toString(node.getIn().toArray()));
            System.out.println("Out: " + Arrays.toString(node.getOut().toArray()));
            System.out.println();
            final Set<Symbol> newVariablesSet = new HashSet<>(node.getIn());
            newVariablesSet.removeAll(node.getOut());

            for(Symbol s: newVariablesSet){
                int num = priorityQueue.remove();
                System.out.println("Using num " + num);
                s.setStackPos(num);
            }
        }
    }
}
