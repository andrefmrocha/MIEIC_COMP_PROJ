import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javamm.semantics.SymbolTable;
import javamm.semantics.MethodSymbolTable;
import javamm.parser.Javamm;
import javamm.parser.SimpleNode;
import javamm.parser.ParseException;
import javamm.SemanticsException;

public class Main {
	
	public static void main(String[] args) {
		PrintWriter writer;
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
			System.out.println("Errors: ");
			for(SemanticsException e: javamm.semanticErrors){
				System.out.println(e.getError() + " in line " + e.getNode().getLine());
			}

			System.out.println("Warnings: ");
			for(SemanticsException e: javamm.semanticWarnings){
				System.out.println(e.getError() + " in line " + e.getNode().getLine());
			}

			if (javamm.semanticErrors.size() > 0)
				throw new RuntimeException();

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
		}catch (ParseException | FileNotFoundException e){
			e.printStackTrace();
			throw new RuntimeException();
		}


	}
}
	

