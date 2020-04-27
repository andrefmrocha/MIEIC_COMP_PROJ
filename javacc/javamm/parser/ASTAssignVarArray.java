package javamm.parser;

import javamm.SemanticsException;
import javamm.semantics.Symbol;
import javamm.semantics.Symbol.Type;

import java.io.PrintWriter;

/* Generated By:JJTree: Do not edit this line. ASTAssignVarArray.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_Javamm=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTAssignVarArray extends TypeNode {
    public String arrayIdentifier;

    public ASTAssignVarArray(int id) {
        super(id);
    }

    public ASTAssignVarArray(Javamm p, int id) {
        super(p, id);
    }

    @Override
    public void eval(Javamm parser) {
        if (this.jjtGetNumChildren() != 2){
            parser.semanticErrors.add(new SemanticsException("Array assignment must have two operators", this));
            return;
        }

        SimpleNode array_access = (SimpleNode) this.jjtGetChild(0);
        SimpleNode assign_arr = (SimpleNode) this.jjtGetChild(1);

        this.evaluateChild(array_access, new Symbol(Type.INT), parser);
        this.evaluateChild(assign_arr, new Symbol(Type.INT), parser);

        this.arrayIdentifier = ((ASTArrayAccess) array_access).arrayIdentifier;

        //TODO assign array Identifier
    }

    @Override
    public void write(PrintWriter writer) {
        Symbol arrayID = this.table.getSymbol(arrayIdentifier);
        ASTArrayAccess arrayOffset = (ASTArrayAccess) this.jjtGetChild(0);
        SimpleNode value = (SimpleNode) this.jjtGetChild(1);

        arrayOffset.writeBody(writer); // push offset to access
        value.write(writer); // push value to assign
        writer.println("  iastore\n"); // store value
    }
}
/* JavaCC - OriginalChecksum=0fabe93ca61357985801f024c411ccd1 (do not edit this line) */
