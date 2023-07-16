import com.sun.source.doctree.TextTree;

import java.io.*;
import java.sql.SQLOutput;
import java.util.Map;

public class Parser {
	private final BufferedReader br;
	private String nextLine;
	public String currentLine;

	public Parser(File file) throws IOException {
		this.br = new BufferedReader(new FileReader(file));
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

		return currentLine.replaceAll("[(|)@]","");
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
