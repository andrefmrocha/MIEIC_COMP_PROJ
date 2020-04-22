/* Generated By:JJTree: Do not edit this line. ASTMethodName.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package javamm.parser;

import javamm.SemanticsException;
import javamm.semantics.MethodIdentifier;
import javamm.semantics.MethodSymbol;
import javamm.semantics.Symbol;

import java.util.ArrayList;
import java.util.List;

public
class ASTMethodName extends SimpleNode {
    public Symbol.Type returnType = null;

    public ASTMethodName(int id) {
        super(id);
    }

    public ASTMethodName(Parser p, int id) {
        super(p, id);
    }

    public void eval(SimpleNode parameters) throws SemanticsException {
        List<Symbol.Type> parametersTypes = new ArrayList<>();

        ASTIdentifier nameNode = (ASTIdentifier) this.jjtGetChild(1);
        SimpleNode typeNode = (SimpleNode) this.jjtGetChild(0);
        String methodName = nameNode.identifierName;
        Symbol.Type type = VarNode.getType(typeNode, table);

        if (parameters != null) {
            parameters.setTables(table, methodTable);
            for (int i = 0; i < parameters.jjtGetNumChildren(); i += 2) {
                VarNode parameter = new VarNode(i, parameters.jjtGetChild(i), parameters.jjtGetChild(i + 1), table,true);
                parameter.eval();
                parametersTypes.add(VarNode.getType((SimpleNode) parameters.jjtGetChild(i), table));
            }
        }
        this.methodTable.putSymbol(new MethodIdentifier(methodName, parametersTypes), new MethodSymbol(type, parametersTypes));
        this.returnType = type;
    }

}
/* JavaCC - OriginalChecksum=4573a3da8b3ef87c2c4dda84d8669778 (do not edit this line) */