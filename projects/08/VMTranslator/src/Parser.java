import java.io.*;

public class Parser {
	private final BufferedReader br;
	private String nextLine;
	String[] currentLine;

	public Parser(File file) throws FileNotFoundException {
		this.br = new BufferedReader(new FileReader(file));
	}

	public boolean hasMoreLines() throws IOException {
		do {
			nextLine = br.readLine();
			if (nextLine == null) return false;
		} while (nextLine.equals("") || nextLine.startsWith("//"));

		nextLine = nextLine.split("//")[0].strip();
		return true;
	}

	public void advance() {
		currentLine = nextLine.split(" ");
	}

	public CommandType commandType() {
		return switch (currentLine[0]) {
			case "push" -> CommandType.C_PUSH;
			case "pop" -> CommandType.C_POP;
			case "add", "sub", "neg", "eq", "gt", "lt", "and", "or", "not" -> CommandType.C_ARITHMETIC;
			case "label" -> CommandType.C_LABEL;
			case "goto" -> CommandType.C_GOTO;
			case "if-goto" -> CommandType.C_IF;
			case "function" -> CommandType.C_FUNCTION;
			case "return" -> CommandType.C_RETURN;
			case "call" -> CommandType.C_CALL;
			default -> throw new IllegalStateException("invalid command type: " + currentLine[0]);
		};
	}

	public String arg1() {
		if (commandType() == CommandType.C_RETURN) {
			throw new IllegalStateException("invalid first argument: " + commandType().name());
		}
		if (commandType() == CommandType.C_ARITHMETIC) return currentLine[0];
		return currentLine[1];
	}

	public int arg2() {
		return switch (commandType()) {
			case C_PUSH, C_POP, C_FUNCTION, C_CALL -> Integer.parseInt(currentLine[2]);
			default ->
				throw new IllegalStateException("Specific arguments(PUSH, POP, FUNCTION, CALL) are only called second argument: " + commandType().name());
		};
	}


}
