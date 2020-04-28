package javamm.parser;

import javamm.SemanticsException;
import javamm.semantics.Symbol;

import java.io.PrintWriter;

/* Generated By:JJTree: Do not edit this line. ASTAssignVar.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_Javamm=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTAssignVar extends TypeNode {
    public String varIdentifier;

    public ASTAssignVar(int id) {
        super(id);
    }

    public ASTAssignVar(Javamm p, int id) {
        super(p, id);
    }

    @Override
    public void eval(Javamm parser) {
        if (this.jjtGetNumChildren() != 2){
            parser.semanticErrors.add(new SemanticsException("Variable assignment must have two operators", this));
            return;
        }

        SimpleNode identifier = (SimpleNode) this.jjtGetChild(0);

        if (identifier.id != JavammTreeConstants.JJTIDENTIFIER){
            parser.semanticErrors.add(new SemanticsException("Variable has not a valid identifier", identifier));
            return;
        }

        ASTIdentifier temp = (ASTIdentifier) identifier;
        varIdentifier = temp.identifierName;

        if (!this.table.checkSymbol(varIdentifier)){
            parser.semanticErrors.add(new SemanticsException("Variable " + varIdentifier + " does not exist", identifier));
            return;
        }

        Symbol symbol = this.table.getSymbol(varIdentifier);

        SimpleNode expression = (SimpleNode) this.jjtGetChild(1);
        this.evaluateChild(expression, symbol, parser);

        symbol.setInitialized();
    }

    @Override
    public void write(PrintWriter writer) {
        String varName = ((ASTIdentifier) this.jjtGetChild(0)).identifierName;
        Symbol leftSymbol = this.table.getSymbol(varName);
        SimpleNode right = (SimpleNode) this.jjtGetChild(1);

        int varNum = leftSymbol.getStackPos();
        if(varNum == -1)
            writer.println("  aload_0");
        // result will be on stack
        right.write(writer);

        if(varNum == -1) {
            String className = this.table.getClassName();
            String jvmType = Symbol.getJVMTypeByType(leftSymbol.getType());
            writer.println("  putfield " + className + "/" + varName + " " + jvmType + "\n");
        } else {
            String storeInstr = Symbol.getJVMPrefix(leftSymbol.getType()) + "store";
            String separator = varNum > 3 ? " " : "_";
            writer.println("  " + storeInstr + separator + Integer.toString(varNum) + "\n");
        }
    }
}
/* JavaCC - OriginalChecksum=661756e145ed220ec46575b3a8adecd3 (do not edit this line) */
