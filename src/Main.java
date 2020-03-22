import java.io.FileNotFoundException;

public class Main {
	
	public static void main(String[] args) {
		System.out.println("Write an arithmetic expression:");
		Parser parser;
		try {
			parser = new Parser(new java.io.FileInputStream(args[0]));
			parser.Program().dump("");
		}catch (ParseException | FileNotFoundException e){
			e.printStackTrace();
			throw new RuntimeException();
		}

		if(parser.nErrors < Parser.NUM_ERRORS)
			throw new RuntimeException();
	}
}
	

