package javamm.cfg;

import java.util.*;

public class CFGNode {
    private final List<CFGNode> edges;
    private final Set<CFGSymbol> usedVars;
    private final Set<CFGSymbol> definedVars;
    private final Set<CFGSymbol> in = new HashSet<>();
    private final Set<CFGSymbol> out = new HashSet<>();

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

    public List<CFGNode> getEdges() {
        return edges;
    }

    public void addEdge(CFGNode node){
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

    public void resetInAndOutDFS() {
        this.visited = true;
        this.in.clear();
        this.out.clear();
        for(CFGNode succ : edges) {
            if(succ.visited) continue; // already visited
            succ.resetInAndOutDFS(); // dfs
        }
    }

    public boolean generateInAndOut() {
        final Set<CFGSymbol> prev_in = new HashSet<>(this.getIn());
        final Set<CFGSymbol> prev_out = new HashSet<>(this.getOut());
        this.updateOutStep();
        this.updateInStep();

        return this.getIn().equals(prev_in) && this.getOut().equals(prev_out);
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

    public boolean isPlaceholder(){
        return this.definedVars.isEmpty() && this.usedVars.isEmpty();
    }

    public void generateStackPos(PriorityQueue<Integer> priorityQueue, Set<CFGSymbol> previousOut, int numParams) {
        visited = true;
        final Set<CFGSymbol> newVariablesSet = new HashSet<>(getOut());

        System.out.println("In " + Arrays.toString(getIn().toArray()));
        System.out.println("Out " + Arrays.toString(getOut().toArray()));
        System.out.println("Used " + Arrays.toString(getUsedVars().toArray()));
        System.out.println("Def " + Arrays.toString(getDefinedVars().toArray()));
        System.out.println("Node " + this);
        System.out.println("Edges " + Arrays.toString(getEdges().toArray()));
        newVariablesSet.removeAll(getIn());
        newVariablesSet.removeAll(previousOut);

        final Set<CFGSymbol> unusedVariables = new HashSet<>(getIn());
        unusedVariables.removeAll(getOut());
        final Set<CFGSymbol> lostVars = new HashSet<>(previousOut);
        lostVars.removeAll(getOut());
        lostVars.removeAll(getIn());

        for (CFGSymbol s : unusedVariables) {
            pushBackPosition(priorityQueue, numParams, s);
        }

        for (CFGSymbol s : lostVars) {
            pushBackPosition(priorityQueue, numParams, s);
        }

        final Set<CFGSymbol> outSymbols = new HashSet<>(getOut());

        for (CFGSymbol s : newVariablesSet) {
            int num = priorityQueue.remove();
            System.out.println("Using num " + num);
            s.getSymbol().setStackPos(num);
            outSymbols.add(s);
        }
        System.out.println();

        for(CFGNode edge: getEdges()){
            if(!edge.visited)
                edge.generateStackPos(priorityQueue, getOut(), numParams);
        }
    }

    private void pushBackPosition(PriorityQueue<Integer> priorityQueue, int numParams, CFGSymbol s) {
        int varNum = s.getSymbol().getStackPos();
        if(varNum < numParams)
            return;
        System.out.println("Pushing back num " + s.getSymbol().getStackPos());
        priorityQueue.offer(varNum);
    }


}
