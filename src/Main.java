import java.io.FileNotFoundException;
import javamm.semantics.SymbolTable;
import javamm.semantics.MethodSymbolTable;
import javamm.parser.Parser;
import javamm.parser.SimpleNode;
import javamm.SemanticsException;
import javamm.parser.ParseException;

public class Main {
	
	public static void main(String[] args) {
		Parser parser;
		try {
			parser = new Parser(new java.io.FileInputStream(args[0]));
			SimpleNode node = parser.Program();
			if(parser.getNumErrors() < Parser.NUM_ERRORS)
				throw new RuntimeException();
			SymbolTable newTable = new SymbolTable();
			MethodSymbolTable methodTable = new MethodSymbolTable();
			node.setTables(newTable, methodTable);
			node.eval();
			node.dump("");
		}catch (ParseException | FileNotFoundException | SemanticsException e){
			e.printStackTrace();
			throw new RuntimeException();
		}


	}
}
	

