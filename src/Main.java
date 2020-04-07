import java.io.FileNotFoundException;

public class Main {
	
	public static void main(String[] args) {
		System.out.println("Write an arithmetic expression:");
		Parser parser;
		try {
			parser = new Parser(new java.io.FileInputStream(args[0]));
			SimpleNode node = parser.Program();
			if(parser.nErrors < Parser.NUM_ERRORS)
				throw new RuntimeException();
			node.dump("");
			node.eval();
		}catch (ParseException | FileNotFoundException | SemanticsException e){
			e.printStackTrace();
			System.out.println(e.toString());
			throw new RuntimeException();
		}


	}
}
	

