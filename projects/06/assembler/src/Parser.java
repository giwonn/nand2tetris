import java.io.*;
import java.sql.SQLOutput;
import java.util.Map;

public class Parser {
	private BufferedReader br;
	private String nextLine;
	public String currentLine;
	private Map<String, Integer> reservedWord;


	public Parser(BufferedReader br) {
		this.br = br;
		reservedWord = Map.ofEntries(
				Map.entry("R0", 0),
				Map.entry("R1", 1),
				Map.entry("R2", 2),
				Map.entry("R3", 3),
				Map.entry("R4", 4),
				Map.entry("R5", 5),
				Map.entry("R6", 6),
				Map.entry("R7", 7),
				Map.entry("R8", 8),
				Map.entry("R9", 9),
				Map.entry("R10", 10),
				Map.entry("R11", 11),
				Map.entry("R12", 12),
				Map.entry("R13", 13),
				Map.entry("R14", 14),
				Map.entry("R15", 15),
				Map.entry("SP", 0),
				Map.entry("LCL", 1),
				Map.entry("ARG", 2),
				Map.entry("THIS", 3),
				Map.entry("THAT", 4),
				Map.entry("SCREEN", 16384),
				Map.entry("KBD", 24576)
		);
	}

	boolean hasMoreLines() {
		try {
			nextLine = br.readLine();
		} catch (IOException e) {
			System.out.println("i/o exception");
			throw new RuntimeException(e);
		}

		if (nextLine == null) return false;

		nextLine = nextLine.split("//")[0].strip();

		return true;
	}

	void advance() {
		if (nextLine == null) return;
		currentLine = nextLine;
	}

	InstructionType instructionType() {
		if (currentLine.startsWith("@")) return InstructionType.A_INSTRUCTION;
		if (currentLine.startsWith("(")) return InstructionType.L_INSTRUCTION;
		return InstructionType.C_INSTRUCTION;
	}

	String symbol() {
		if (instructionType() != InstructionType.L_INSTRUCTION && instructionType() != InstructionType.A_INSTRUCTION) {
			throw new Error("invalid call function - symbol() only called when instruction type is 'L' or 'A'");
		}

		String symbol = currentLine.replaceAll("[(|)@]","");
		return reservedWord.containsKey(symbol) ? reservedWord.get(symbol).toString() : symbol;
	}

	String dest() {
		if (instructionType() != InstructionType.C_INSTRUCTION) {
			throw new Error("invalid call function - dest() only called when instruction type is 'C'");
		}

		return currentLine.contains("=") ? currentLine.split("=")[0] : "";
	}

	String comp() {
		if (instructionType() != InstructionType.C_INSTRUCTION) {
			throw new Error("invalid call function - comp() only called when instruction type is 'C'");
		}

		if (!currentLine.contains("=")) return currentLine.split(";")[0];

		return currentLine.split("=")[1].split(";")[0];
	}

	String jump() {
		return currentLine.contains(";") ? currentLine.split(";")[1] : "";
	}
}
