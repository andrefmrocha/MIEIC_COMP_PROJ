package javamm.parser;

import javamm.SemanticsException;
import javamm.cfg.CFGNode;
import javamm.cfg.Graph;
import javamm.semantics.StackUsage;
import javamm.semantics.Symbol;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

/* Generated By:JJTree: Do not edit this line. ASTMethodBody.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_Javamm=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTMethodBody extends SimpleNode {
    public Symbol.Type returnType = null;
    public int localsCount = 0; // count locals in method body
    private HashMap<Integer, Integer> requiredPops = new HashMap<>();
    public Graph graph = null;

    public ASTMethodBody(int id) {
        super(id);
    }

    public ASTMethodBody(Javamm p, int id) {
        super(p, id);
    }

    public void eval(Javamm parser, int stackPointer) {
        boolean foundReturn = false; // if found return node
        List<CFGNode> nodes = new ArrayList<>(); // collect all CFGNodes inside this method

        for (int i = 0; i < this.jjtGetNumChildren(); i++) {
            SimpleNode methodNode = (SimpleNode) this.jjtGetChild(i);
            if (methodNode.id == JavammTreeConstants.JJTVAR){ // var definition
                ASTVar var = (ASTVar) methodNode;
                var.stackPos = stackPointer++; // assign initial register to each variable
                localsCount++;
            }
            methodNode.setTables(table, methodTable);
            if (methodNode.id == JavammTreeConstants.JJTMETHODRETURN) {
                foundReturn = true;
                final ASTMethodReturn methodReturn = (ASTMethodReturn) methodNode;
                methodReturn.expectedType = returnType;
                methodNode.eval(parser);
                addNodesToGraph(nodes, methodNode);
                break;
            }

            // invalid statement found, such as '1;'
            if (!methodNode.validStatement) {
                parser.semanticErrors.add(new SemanticsException("Not a statement: " + methodNode.toString(), methodNode));
                return;
            }

            methodNode.eval(parser);
            addNodesToGraph(nodes, methodNode);
        }
        if(!foundReturn && returnType != Symbol.Type.VOID) {
            parser.semanticErrors.add(new SemanticsException("Return not found. Must return: " + returnType,this));
        }

        this.graph = new Graph(nodes); // build CFGNode graph, used for dataflow analysis

        checkUsedSymbols(this);
    }

    /**
     * Verify which assignments are effectively used or could be ignored
     * @param node Node to start dfs
     */
    private void checkUsedSymbols(SimpleNode node) {
        for(int i = 0; i < node.jjtGetNumChildren(); i++){
            SimpleNode methodNode = (SimpleNode) node.jjtGetChild(i);
            switch (methodNode.id) {
                case JavammTreeConstants.JJTASSIGNVAR:
                    ((ASTAssignVar) methodNode).isUsedSymbol();
                    break;
                case JavammTreeConstants.JJTWHILE:
                    checkUsedSymbols(methodNode);
                    break;
                case JavammTreeConstants.JJTIF:
                    checkUsedSymbols((SimpleNode) methodNode.jjtGetChild(1));
                    checkUsedSymbols((SimpleNode) methodNode.jjtGetChild(2));
                    break;
            }
        }
    }

    private void addNodesToGraph(List<CFGNode> graph, SimpleNode methodNode) {
        List<CFGNode> nodes = methodNode.getNodes();

        if(graph.size() != 0)
            graph.get(graph.size() - 1).addEdge(nodes.get(0));

        graph.addAll(nodes);
    }

    public void write(PrintWriter writer) {
        for(int i = 0; i< this.jjtGetNumChildren(); i++) {
            SimpleNode node = (SimpleNode) this.jjtGetChild(i);
            node.write(writer);
            StackUsage.popStack(writer, this.requiredPops.get(i)); // pop any value left on the stack
        }
    }

    @Override
    protected void calculateStackUsage(StackUsage stackUsage) {
        int stackUsageBefore = stackUsage.getStackUsage();
        for(int i = 0; i < this.jjtGetNumChildren(); i++) {
            SimpleNode child = (SimpleNode) this.jjtGetChild(i);
            child.calculateStackUsage(stackUsage);
            this.requiredPops.put(i, stackUsage.getStackUsage() - stackUsageBefore); // check if any value was left in stack
            stackUsage.set(stackUsageBefore); // reset stackUsage value to previous one
        }
    }

}
/* JavaCC - OriginalChecksum=5c687ef179ba60ab5c8f745d7c137cbc (do not edit this line) */
