package javamm.cfg;

import java.util.*;

/**
 * CFG graph container. Contains all algorithms necessary by it
 */
public class Graph {
    private final List<CFGNode> nodes;
    private CFGNode root;

    public Graph(List<CFGNode> nodes) {
        this.nodes = removePlaceHolders(nodes);
        generateInAndOut();
    }

    /**
     * Removes placeholder nodes. The definition of a placeholder can be found
     * in @link CFGNode.isPlaceholder. Placeholder edges will then point towards the nodes that
     * point towards it
     * @param nodes - list of the current graph nodes
     * @return list of nodes with placeholders parse out.
     */
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
                if (edge.isPlaceholder()) { // If an edge is placeholder, finds the next one that isn't
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

    /**
     * Generate Live-In and Live-out sets for all nodes of the graph
     */
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

    /**
     * Reset In and Out sets
     */
    private void resetInAndOut() {
        root.resetInAndOutDFS();
        resetVisited();
    }

    /**
     * Reset visited flag for all nodes in the graph
     */
    private void resetVisited() {
        for (CFGNode node: nodes)
            node.visited = false;
    }

    /**
     * Use the graph coloring algorithm along with dataflow analysis
     * to minimize the number of registers used to store variables in
     * a given method
     * @param maxSize - maximum number of variables to be used
     * @param numParams - number of parameters a function as, which will limit the starting point of the allocation
     * @return number of variables used
     */
    public int generateStackPos(int maxSize, int numParams){
        Map<String, LiveNode> graph = new HashMap<>();
        for(CFGNode node: nodes){
            for(CFGSymbol symbol: node.getDefinedVars()){
                graph.put(symbol.getIdentifier(), new LiveNode(symbol));
            }
        }

        for(CFGNode node: nodes){
            Set<CFGSymbol> nodesInCommon = new HashSet<>(node.getOut());
            nodesInCommon.retainAll(node.getIn());
            nodesInCommon.addAll(node.getDefinedVars());
            addInterferences(graph, new ArrayList<>(node.getIn()));
            addInterferences(graph, new ArrayList<>(node.getOut()));
            addInterferences(graph, new ArrayList<>(nodesInCommon));
        }

        return graph.isEmpty() ? numParams : colorGraph(graph, maxSize, numParams);
    }

    /**
     * Graph coloring algorithm, which is used to attribute virtual JVM registers
     * to the variables in a method
     * @param graph - the interference graph
     * @param maxSize - maximum number of variables to be used
     * @param numParams - number of parameters a function as, which will limit the starting point of the allocation
     * @return number of variables used
     */
    private int colorGraph(Map<String, LiveNode> graph, int maxSize, int numParams) {
        Stack<LiveNode> stack = new Stack<>();
        graph.values().forEach(LiveNode::reset);

        for(int i = 0; i < graph.size(); i++){
            graph.values().forEach(node -> {
                if(!node.isVisited()){
                    if(node.getColoringEdges().size() < (maxSize - numParams)){
                        stack.push(node);
                        node.visitNode();
                    }
                }
            });
        }

        if(stack.size() < graph.size()){
            return -1;
        }

        int maxPos = -1;
        while (!stack.isEmpty()){
            final LiveNode node = stack.pop();
            int pos = -1 + numParams;
            for(LiveNode n: node.getEdges())
                pos = Math.max(pos, n.getStackPos());
            node.setStackPos(pos + 1);
            maxPos = Math.max(maxPos, pos + 1);
        }
        return maxPos + 1;
    }

    /**
     * Build the interference graph from the existing information from the dataflow analysis
     * @param graph the given graph
     * @param nodesToAdd nodes to be added as interferences
     */
    private void addInterferences(Map<String, LiveNode> graph, List<CFGSymbol> nodesToAdd) {
        for(int i = 0; i < nodesToAdd.size(); i++){
            for(int j = i + 1; j < nodesToAdd.size(); j++){
                final LiveNode node1 = graph.get(nodesToAdd.get(i).getIdentifier());
                final LiveNode node2 = graph.get(nodesToAdd.get(j).getIdentifier());
                if(node1 == null || node2 == null){
                    continue;
                }
                node1.addEdge(node2);
                node2.addEdge(node1);
            }
        }
    }
}
