import java.io.FileNotFoundException;
import semantics.SymbolTable;
public class Main {
	
	public static void main(String[] args) {
		Parser parser;
		try {
			parser = new Parser(new java.io.FileInputStream(args[0]));
			SimpleNode node = parser.Program();
			if(parser.nErrors < Parser.NUM_ERRORS)
				throw new RuntimeException();
			SymbolTable newTable = new SymbolTable();
			node.setTable(newTable);
			node.eval();
			node.dump("");
		}catch (ParseException | FileNotFoundException | SemanticsException e){
			e.printStackTrace();
			throw new RuntimeException();
		}


	}
}
	

