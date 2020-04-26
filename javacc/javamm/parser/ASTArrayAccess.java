package javamm.parser;

import javamm.SemanticsException;
import javamm.semantics.Symbol;
import javamm.semantics.Symbol.Type;

import java.io.PrintWriter;

import static javamm.parser.JavammTreeConstants.JJTIDENTIFIER;

/* Generated By:JJTree: Do not edit this line. ASTArrayAccess.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_Javamm=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTArrayAccess extends TypeNode {
    public String arrayIdentifier;

    public ASTArrayAccess(int id) {
        super(id);
        type = Type.INT;  //TODO: Adapt if String[] is to be used
    }

    public ASTArrayAccess(Javamm p, int id) {
        super(p, id);
        type = Type.INT;  //TODO: Adapt if String[] is to be used
    }

    @Override
    public void eval(Javamm parser) {
        if (this.jjtGetNumChildren() != 2){
            parser.semanticErrors.add(new SemanticsException("Array access require identifier and index", this));
            return;
        }

        SimpleNode arrayName = (SimpleNode) this.jjtGetChild(0);
        SimpleNode indexValue = (SimpleNode) this.jjtGetChild(1);

        if(arrayName.id != JJTIDENTIFIER){
            parser.semanticErrors.add(new SemanticsException("First child of Array Access must be an identifier", this));
            return;
        }
        arrayName.setTables(this.table, this.methodTable);
        indexValue.setTables(this.table, this.methodTable);

        this.arrayIdentifier = ((ASTIdentifier) arrayName).identifierName;

        this.evaluateChild(arrayName, new Symbol(Type.INT_ARRAY), parser);
        this.evaluateChild(indexValue, new Symbol(Type.INT), parser);
    }

    @Override
    public void write(PrintWriter writer) {
        SimpleNode identifier = (SimpleNode) this.jjtGetChild(0);
        SimpleNode offset = (SimpleNode) this.jjtGetChild(1);
        identifier.write(writer);
        offset.write(writer);
        writer.println("iaload");
    }
}
/* JavaCC - OriginalChecksum=07823d6065ca9b37f085148b14d167b9 (do not edit this line) */
