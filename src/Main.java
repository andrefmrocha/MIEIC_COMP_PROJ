import java.io.FileNotFoundException;
import javamm.semantics.SymbolTable;
import javamm.semantics.MethodSymbolTable;
import javamm.parser.Javamm;
import javamm.parser.SimpleNode;
import javamm.parser.ParseException;
import javamm.SemanticsException;

public class Main {
	
	public static void main(String[] args) {
		Javamm javamm;
		try {
			javamm = new Javamm(new java.io.FileInputStream(args[0]));
			SimpleNode node = javamm.Program();
			if(javamm.getNumErrors() < javamm.NUM_ERRORS)
				throw new RuntimeException();
			SymbolTable newTable = new SymbolTable();
			MethodSymbolTable methodTable = new MethodSymbolTable();
			node.setTables(newTable, methodTable);
			node.eval(javamm);
			for(SemanticsException e: javamm.semanticErrors){
				System.out.println(e.getError() + " in line " + e.getNode().getLine());
			}

			if (javamm.semanticErrors.size() > 0)
				throw new RuntimeException();

			node.dump("");
		}catch (ParseException | FileNotFoundException e){
			e.printStackTrace();
			throw new RuntimeException();
		}


	}
}
	

