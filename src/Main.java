import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javamm.semantics.SymbolTable;
import javamm.semantics.MethodSymbolTable;
import javamm.parser.Parser;
import javamm.parser.SimpleNode;
import javamm.SemanticsException;
import javamm.parser.ParseException;

public class Main {
	
	public static void main(String[] args) {
		Parser parser;
		PrintWriter writer;
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
			Matcher fileMatch = Pattern.compile("(?<=/)?(\\w)+.jmm$").matcher(args[0]);
			if(!fileMatch.find())
				throw new IllegalArgumentException();
			String generated_name = fileMatch.group().replace(".jmm",".j");
			File file = new File("jasmin_gen/" + generated_name);
			file.getParentFile().mkdirs();
			writer = new PrintWriter("jasmin_gen/" + generated_name);
			writer.println(".source " + generated_name);
			node.write(writer);
			writer.close();
		}catch (ParseException | FileNotFoundException | SemanticsException e){
			e.printStackTrace();
			throw new RuntimeException();
		}


	}
}
	

