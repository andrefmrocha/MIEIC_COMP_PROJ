package javamm.parser;

import javamm.SemanticsException;
import javamm.semantics.ClassSymbol;
import javamm.semantics.Symbol;

/* Generated By:JJTree: Do not edit this line. ASTExtend.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_Javamm=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTExtend extends SimpleNode {
    public ClassSymbol extendedClass;

    public ASTExtend(int id) {
        super(id);
    }

    public ASTExtend(Javamm p, int id) {
        super(p, id);
    }

    public void eval(Javamm parser) {

        if (this.jjtGetNumChildren() != 1) {
            parser.semanticErrors.add(new SemanticsException("Lacks the number of required children!", this));
            return;
        }

        SimpleNode child = (SimpleNode) this.jjtGetChild(0);

        if (child.id != JavammTreeConstants.JJTIDENTIFIER) {
            parser.semanticErrors.add(new SemanticsException("Parameter has not a valid identifier", child));
            return;
        }

        ASTIdentifier temp = (ASTIdentifier) child;
        String name = temp.identifierName;

        if (!this.table.checkSymbol(name)) {
            parser.semanticErrors.add(new SemanticsException("Class " + name + " was not found", temp));
            return;
        }

        Symbol identifier = this.table.getSymbol(name);

        if (identifier.getType() != Symbol.Type.CLASS) {
            parser.semanticErrors.add(new SemanticsException(name + " is not a class, got " + identifier.getType(), temp));
            return;
        }

        extendedClass = (ClassSymbol) identifier;
        methodTable.getTable().putAll(extendedClass.getMethods().getTable());
    }

}
/* JavaCC - OriginalChecksum=e49346eb773d71c8a3e9bf74b151c1de (do not edit this line) */
