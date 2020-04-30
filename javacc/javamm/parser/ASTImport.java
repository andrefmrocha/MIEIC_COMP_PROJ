package javamm.parser;

import javamm.SemanticsException;
import javamm.semantics.ClassSymbol;
import javamm.semantics.MethodIdentifier;
import javamm.semantics.MethodSymbol;
import javamm.semantics.Symbol;
import javamm.semantics.Symbol.Type;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/* Generated By:JJTree: Do not edit this line. ASTImport.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_Javamm=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTImport extends SimpleNode {

    public boolean isStatic;

    public ASTImport(int id) {
        super(id);
    }

    public ASTImport(Javamm p, int id) {
        super(p, id);
    }

    public void eval(Javamm parser) {

        Type returnValue = Type.VOID;
        List<String> identifiers = new ArrayList<>();
        List<Type> params = new ArrayList<>();
        for (int i = 0; i < this.jjtGetNumChildren(); i++) {
            SimpleNode currNode = (SimpleNode) this.jjtGetChild(i);
            currNode.setTables(table, methodTable);
            currNode.eval(parser);

            if (currNode.id == JavammTreeConstants.JJTIMPORTPARAMS) {
                params = ((ASTImportParams) currNode).paramTypes;
            } else if (currNode.id == JavammTreeConstants.JJTRETURN) {
                returnValue = ((ASTReturn) currNode).returnType;
            } else if (currNode.id == JavammTreeConstants.JJTIDENTIFIER) {
                identifiers.add(((ASTIdentifier) currNode).identifierName);
            }
        }

        String fullImportName = String.join(".", identifiers);
        if (identifiers.size() == 1) {

            if (table.checkSymbol(fullImportName)) {
                Symbol symbol = table.getSymbol(fullImportName);
                if (symbol.getType() != Type.CLASS) {
                    parser.semanticErrors.add(new SemanticsException("Expected to find class, found " + symbol.getType(), this));
                    return;
                }
                ((ClassSymbol) symbol).getConstructors().putSymbol(
                        new MethodIdentifier(ClassSymbol.init, params), new MethodSymbol(returnValue, params));
            } else
                table.putSymbol(fullImportName, new ClassSymbol(fullImportName, params));


        } else if (this.isStatic)
            methodTable.putSymbol(new MethodIdentifier(fullImportName, params), new MethodSymbol(returnValue, params));
        else {
            String methodName = identifiers.get(1);
            String className = identifiers.get(0);
            if (!table.checkSymbol(className)) {
                parser.semanticErrors.add(new SemanticsException("Class " + className + " has not been imported", this));
                return;
            }

            Symbol symbol = table.getSymbol(className);
            if (symbol.getType() != Type.CLASS) {
                parser.semanticErrors.add(new SemanticsException(className + " is not a class", this));
                return;
            }

            ClassSymbol classSymbol = (ClassSymbol) symbol;
            classSymbol.getMethods().putSymbol(new MethodIdentifier(methodName, params), new MethodSymbol(returnValue, params));
        }
    }

    @Override
    public void write(PrintWriter writer) {
    }

}
/* JavaCC - OriginalChecksum=102b8623e78d8554f34992788a15d8e1 (do not edit this line) */
