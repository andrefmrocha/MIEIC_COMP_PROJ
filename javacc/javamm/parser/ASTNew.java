package javamm.parser;

import javamm.SemanticsException;
import javamm.semantics.ClassSymbol;
import javamm.semantics.MethodIdentifier;
import javamm.semantics.StackUsage;
import javamm.semantics.Symbol;
import javamm.semantics.Symbol.Type;

import java.io.PrintWriter;

/* Generated By:JJTree: Do not edit this line. ASTNew.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_Javamm=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTNew extends TypeNode {
    public ClassSymbol classSymbol;
    public String identifier;
    private MethodIdentifier constructor;

    public ASTNew(int id) {
        super(id);
        type = Type.CLASS;
        this.validStatement = true;
    }

    public ASTNew(Javamm p, int id) {
        super(p, id);
        type = Type.CLASS;
        this.validStatement = true;
    }

    @Override
    public void eval(Javamm parser) {
        SimpleNode child = (SimpleNode) this.jjtGetChild(0);
        this.evaluateChild(child, new Symbol(type), parser);

        ASTIdentifier identifier = (ASTIdentifier) child;
        classSymbol = (ClassSymbol) table.getSymbol(identifier.identifierName);
        final ASTCallParams callParams = ((ASTCallParams) this.jjtGetChild(1));
        callParams.setTables(table, methodTable);
        constructor = callParams.getMethodIdentifier(ClassSymbol.init, parser);
        this.identifier = identifier.identifierName;

        if (!classSymbol.getConstructors().checkSymbol(constructor))
            parser.semanticErrors.add(new SemanticsException("Can't find constructor with that signature", callParams));


    }

    @Override
    public void write(PrintWriter writer) {
        writer.println("  new " + identifier); // create new reference
        writer.println("  dup"); //duplicate object on top of stack, need 2 references (1 constructor + 1 assign)
        ((SimpleNode) this.jjtGetChild(1)).write(writer);
        writer.print("  invokespecial " + identifier + "/<init>("); // call constructor
        for(Type type : constructor.getParameters()){
            writer.print(Symbol.getJVMTypeByType(type));
        }
        writer.println(")V");
    }

    @Override
    protected void calculateStackUsage(StackUsage stackUsage) {
        stackUsage.inc(2);

        ASTCallParams params = (ASTCallParams) this.jjtGetChild(1);
        params.calculateStackUsage(stackUsage);
        stackUsage.dec(params.nParams + 1);
    }

}
/* JavaCC - OriginalChecksum=c6d588009442d8c81f835326710afcd3 (do not edit this line) */
