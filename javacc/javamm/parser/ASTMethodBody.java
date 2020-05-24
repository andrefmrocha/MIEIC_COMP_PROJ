package javamm.parser;

import javamm.SemanticsException;
import javamm.semantics.Symbol;

/* Generated By:JJTree: Do not edit this line. ASTMethodBody.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_Javamm=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTMethodBody extends SimpleNode {
    public Symbol.Type returnType = null;
    public int localsCount = 0;

    public ASTMethodBody(int id) {
        super(id);
    }

    public ASTMethodBody(Javamm p, int id) {
        super(p, id);
    }

    public void eval(Javamm parser, int stackPointer) {
        boolean foundReturn = false;
        for (int i = 0; i < this.jjtGetNumChildren(); i++) {
            SimpleNode methodNode = (SimpleNode) this.jjtGetChild(i);
            if (methodNode.id == JavammTreeConstants.JJTVAR){
                ASTVar var = (ASTVar) methodNode;
                var.stackPos = stackPointer++;
                localsCount++;
            }
            methodNode.setTables(table, methodTable);
            if (methodNode.id == JavammTreeConstants.JJTMETHODRETURN) {
                foundReturn = true;
                final ASTMethodReturn methodReturn = (ASTMethodReturn) methodNode;
                methodReturn.expectedType = returnType;
                methodNode.eval(parser);
                break;

            }

            if (!methodNode.validStatement) {
                parser.semanticErrors.add(new SemanticsException("Not a statement: " + methodNode.toString(), methodNode));
                return;
            }

            methodNode.eval(parser);
        }
        if(!foundReturn && returnType != Symbol.Type.VOID) {
            parser.semanticErrors.add(new SemanticsException("Return not found. Must return: " + returnType,this));
        }
    }

}
/* JavaCC - OriginalChecksum=5c687ef179ba60ab5c8f745d7c137cbc (do not edit this line) */
