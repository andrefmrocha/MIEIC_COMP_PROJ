import java.io.*;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javamm.parser.ASTIf;
import javamm.parser.ASTWhile;
import javamm.semantics.SymbolTable;
import javamm.semantics.MethodSymbolTable;
import javamm.parser.Javamm;
import javamm.parser.SimpleNode;
import javamm.parser.ParseException;
import javamm.SemanticsException;

public class Main {
    public static final String JASMIN_GEN = "jasmin_gen/";
    private static boolean debugMode = false;

    public static void main(String[] args) {
        PrintWriter writer;
        Javamm javamm;
        try {

            //setup
            String filename = checkDebugMode(args);
            Matcher fileMatch = Pattern.compile("(?<=/)?(\\w)+.jmm$").matcher(filename);
            if (!fileMatch.find())
                throw new IllegalArgumentException("File has not the correct extension: .jmm");
            javamm = new Javamm(new java.io.FileInputStream(filename));
            SimpleNode node = javamm.Program();
            if (javamm.getNumErrors() < javamm.NUM_ERRORS)
                throw new RuntimeException();
            SymbolTable newTable = new SymbolTable();
            MethodSymbolTable methodTable = new MethodSymbolTable();

            //syntax and semantic analysis
            node.setTables(newTable, methodTable);
            node.eval(javamm);

            if (debugMode) {
                node.dump("");
                node.printTable();
            }

			if (javamm.semanticWarnings.size() > 0 ) {
				System.out.println("Warnings: ");
				for(SemanticsException e: javamm.semanticWarnings){
					System.out.println(e.getError() + " in line " + e.getNode().getLine());
				}
			}

			if (javamm.semanticErrors.size() > 0) {
				System.out.println("Errors: ");
				for(SemanticsException e: javamm.semanticErrors){
					System.out.println(e.getError() + " in line " + e.getNode().getLine());
				}
				throw new RuntimeException();
			} else System.out.println("Analysis completed successfully. Moving on to code generation...");

            //code generation
            ASTIf.labelCounter = 0;
            ASTWhile.labelCounter = 0;
            String assemblerName = fileMatch.group().replace(".jmm", ".j");
            File file = new File(JASMIN_GEN + assemblerName);
            file.getParentFile().mkdirs();
            writer = new PrintWriter(JASMIN_GEN + assemblerName);
            writer.println(".source " + assemblerName);
            node.write(writer);
            writer.close();
            System.out.println("Code generated successfully. The files are located in the folder 'jasmin_gen' in the project root.");
            generateClassFile(assemblerName);
        } catch (ParseException | FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }


    }

    private static void generateClassFile(String assemblerName) {
        boolean isWindows = System.getProperty("os.name")
                .toLowerCase().startsWith("windows");

        ProcessBuilder builder = new ProcessBuilder();
        String command = "java -jar jasmin/jasmin.jar " + JASMIN_GEN + assemblerName + " -d out";
        System.out.println("Executing " + command);
        if (isWindows) {
            builder.command("cmd.exe", "/c", command);
        } else {
            builder.command("sh", "-c", command);
        }
        try {
            Process process = builder.start();
            System.out.println("Generated class file with return code " + process.waitFor());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static String checkDebugMode(String[] args) throws ParseException {
        if (args.length > 2 || args.length < 1)
            throw new IllegalArgumentException("At least one argument needed and at most two arguments allowed: [ -d ] <filename>");
        if (args.length < 2 && args[0].equals("-d"))
            throw new IllegalArgumentException("No file selected: [ -d] <filename>");
        if (args.length > 1 && args[1].equals("-d"))
            throw new IllegalArgumentException("Wrong usage: [ -d ] <filename>");
        if (args[0].equals("-d")) {
            debugMode = true;
            return args[1];
        } else
            return args[0];
    }
}
	

