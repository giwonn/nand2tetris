import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CodeWriter {
	private final PrintWriter pw;
	private final String fileName;
	private int currentLine;

	List<String> calculationArithmeticList = Arrays.asList("add", "sub", "eq", "gt", "lt", "and", "or");

	public CodeWriter(String filePath) throws IOException {
		File file = new File(filePath);
		String asmFilePath = file.getCanonicalPath().replace(".vm", ".asm");
		fileName = file.getName();
		pw = new PrintWriter(asmFilePath);
		currentLine = 0;
	}

	private void addLine(String command) {
		pw.println(command);
		currentLine++;
	}

	public void writeArithmetic(String command) {
		addLine("@SP" + " // " + command);
		addLine("A=M-1");

		if (calculationArithmeticList.contains(command)) {
			prependArithmeticCommand();
		}

		arithmeticCommand(command);

		if (calculationArithmeticList.contains(command)) {
			appendArithmeticCommand();
		}
	}

	private void prependArithmeticCommand() {
		addLine("D=M");
		addLine("A=A-1");
	}

	private void arithmeticCommand(String command) {
		switch(command) {
			case "add" -> addLine("M=M+D");
			case "sub" -> addLine("M=M-D");
			case "and" -> addLine("M=M&D");
			case "or" -> addLine("M=M|D");
			case "neg" -> addLine("M=-M");
			case "not" -> addLine("M=!M");
			case "eq", "gt", "lt" -> {
				addLine("D=M-D");
				addLine("M=-1");

				addLine("@" + (currentLine + 6));
				addLine("D;J"+command.toUpperCase());

				addLine("@SP");
				addLine("A=M-1");
				addLine("A=A-1");
				addLine("M=0");
			}
		}
	}

	private void appendArithmeticCommand() {
		addLine("@SP");
		addLine("M=M-1");
	}

	public void writePushPop(CommandType commandType, String segment, int index) {
		if (commandType != CommandType.C_PUSH && commandType != CommandType.C_POP) {
			throw new IllegalArgumentException("commandType must be C_PUSH or C_POP");
		}
		if (segment.equals("pointer") && !(index == 0 || index == 1)) {
			throw new IllegalArgumentException("If command is 'pointer', index must be 0 or 1: " + index);
		}
		if (segment.equals("temp") && !(index >= 0 && index <= 7)) {
			throw new IllegalArgumentException("If command is 'temp', index must be 0~7: " + index);
		}

		String mappingAddress = switch (segment) {
			case "local" -> "LCL";
			case "argument" -> "ARG";
			case "this" -> "THIS";
			case "that" -> "THAT";
			case "pointer" -> String.valueOf(index + 3);
			case "temp" ->  String.valueOf(index + 5);
			case "constant" -> String.valueOf(index);
			case "static" -> fileName + "." + index;
			default -> throw new IllegalArgumentException("Unexpected segment: " + segment);
		};

		if (commandType == CommandType.C_PUSH) {
			addLine("@" + mappingAddress + " // push " + segment + " " + index);

			if (isInteger(mappingAddress)) {
				addLine(segment.equals("constant") ? "D=A" : "D=M");
			} else {
				addLine("A=M");
				for (int i = 0; i < index; i++) {
					addLine("A=A+1");
				}
				addLine("D=M");
			}

			addLine("@SP");
			addLine("A=M");
			addLine("M=D");
			addLine("@SP");
			addLine("M=M+1");
		}

		if (commandType == CommandType.C_POP) {
			addLine("@SP" + " // pop " + segment + " " + index);
			addLine("A=M-1");
			addLine("D=M");
			addLine("@" + mappingAddress);

			if (!isInteger(mappingAddress)) {
				addLine("A=M");
				for (int i = 0; i < index; i++) {
					addLine("A=A+1");
				}
			}

			addLine("M=D");
			addLine("@SP");
			addLine("M=M-1");
		}
	}

	public void close() {
		addLine("@" + currentLine + " // close stream");
		addLine("0;JMP");

		pw.flush();
		pw.close();
	}

	private boolean isInteger(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
