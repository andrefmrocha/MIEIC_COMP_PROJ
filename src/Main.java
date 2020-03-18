public class Main {
	
	public static void main(String[] args) {
		System.out.println("Write an arithmetic expression:");
		try {
			Parser parser = new Parser(System.in);
			parser.Program().dump("");
		}catch (ParseException e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
}
	

