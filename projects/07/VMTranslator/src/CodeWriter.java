import java.io.*;

public class CodeWriter {
	private final PrintWriter pw;
	private final String fileName;
	private int currentLine;

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

	private boolean commandEqualCheck(String a, String b) {
		if (!a.equals(b)) return false;

		pw.println("// " + a);
		return true;
	}

	public void writeArithmetic(String command) {
		addLine("@SP" + " // " + command);
		addLine("A=M-1");

		switch (command) {
			case "add", "sub", "eq", "gt", "lt", "and", "or" -> {
				addLine("D=M");
				addLine("A=A-1");
			}
		}

		if (command.equals("add")) {
			// x = x + y
			addLine("M=M+D");
		}

		if (command.equals("sub")) {
			// x = x - D = x - y
			addLine("M=M-D");
		}

		if (command.equals("eq")) {
			addLine("D=M-D");
			addLine("M=0");

			addLine("@" + (currentLine + 6));
			addLine("D;JNE");

			addLine("@SP");
			addLine("A=M-1");
			addLine("A=A-1");
			addLine("M=-1");
		}

		if (command.equals("gt")) {
			addLine("D=M-D");
			addLine("M=0");

			addLine("@" + (currentLine + 6));
			addLine("D;JLE");

			addLine("@SP");
			addLine("A=M-1");
			addLine("A=A-1");
			addLine("M=-1");
		}

		if (command.equals("lt")) {
			// D = x - y
			addLine("D=M-D");
			// x 자리 0으로 미리 초기화
			addLine("M=0");

			// D가 0 이상이면 점프
			addLine("@" + (currentLine + 6));
			addLine("D;JGE");

			// 점프안하면 D가 음수이므로 x로 이동하여 -1로 초기화
			addLine("@SP");
			addLine("A=M-1");
			addLine("A=A-1");
			addLine("M=-1");
		}

		if (command.equals("and")) {
			addLine("M=M&D");
		}

		if (command.equals("or")) {
			addLine("M=M|D");
		}

		if (command.equals("neg")) {
			addLine("M=-M");
		}

		if (command.equals("not")) {
			addLine("M=!M");
		}

		switch (command) {
			case "add", "sub", "eq", "gt", "lt", "and", "or" -> {
				addLine("@SP");
				addLine("M=M-1");
			}
		}
	}

	public void writePushPop(CommandType commandType, String segment, int index) {
		if (commandType != CommandType.C_PUSH && commandType != CommandType.C_POP) {
			throw new IllegalArgumentException("commandType must be C_PUSH or C_POP");
		}

		String mappingAddress = switch (segment) {
			case "local" -> "LCL";
			case "argument" -> "ARG";
			case "this" -> "THIS";
			case "that" -> "THAT";
			case "pointer" -> {
				if (!(index == 0 || index == 1)) {
					throw new IllegalArgumentException("If command is 'pointer', index must be 0 or 1: " + index);
				}
				yield String.valueOf(index + 3);
			}
			case "temp" -> {
				if (index < 0 || index > 7) {
					throw new IllegalArgumentException("If command is 'temp', index must be 0~7: " + index);
				}
				yield String.valueOf(index + 5);
			}
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
