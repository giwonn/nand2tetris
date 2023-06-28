import java.io.*;

public class Main {

	public static void main(String[] args) {

		if (args.length == 0) {
			System.out.println("Please input File Name!");
		}

		for (String filePath : args) {
			try {
					if (!filePath.endsWith(".asm")) {
						throw new FileNotFoundException();
					}

					File file = new File(filePath);
					SymbolTable symbolTable = createSymbolTable(file);
					Code code = new Code();

					String fileName = file.getName().split("\\.")[0];
					File hackFile = new File(file.getAbsolutePath().replace(file.getName(), "") + fileName + ".hack");

					BufferedWriter writer = new BufferedWriter(new FileWriter(hackFile));
					Parser parser = new Parser(new BufferedReader(new FileReader(file)));

					int variableAddress = 16;

					while(parser.hasMoreLines()) {
						parser.advance();
						// 주석이거나 빈 라인인 경우 스킵
						if (parser.currentLine.equals("")) continue;

						// C-명령어인 경우 comp, dest, jump 파싱하여 write
						if (parser.instructionType() == InstructionType.C_INSTRUCTION) {
							writer.write("111" + code.comp(parser.comp()) + code.dest(parser.dest()) + code.jump(parser.jump()) + "\n");
						}

						// A-명령어인 경우 2진수로 변환하여 write
						if (parser.instructionType() == InstructionType.A_INSTRUCTION) {
							String symbol = parser.symbol();

							String address;
							// symbol이 숫자일 경우
							if (symbol.chars().allMatch(Character::isDigit)) {
								address = Integer.toBinaryString(Integer.parseInt(symbol));
							} else if (symbolTable.contains(symbol)) { // 문자일 경우 && symbolTable에 존재할 경우
								address = Integer.toBinaryString(symbolTable.getAddress(symbol) + 16);
							} else {
								symbolTable.addEntry(symbol, variableAddress);
								variableAddress++;
								address = Integer.toBinaryString(variableAddress);
							}
							String address16Bit = String.format("%016d", Long.parseLong(address));
							writer.write(address16Bit + "\n");
						}

					}

					writer.flush();
					writer.close();
			} catch (FileNotFoundException e) {
				if (!filePath.endsWith(".asm")) {
					System.out.println("File extension is not .asm");
				} else {
					e.printStackTrace();
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}


		}

	}

	static SymbolTable createSymbolTable(File file) throws FileNotFoundException {
		Parser parser = new Parser(new BufferedReader(new FileReader(file)));

		SymbolTable symbolTable = new SymbolTable();
		int lineNumber = 0;

		while(parser.hasMoreLines()) {
			parser.advance();
			switch(parser.instructionType()) {
				case C_INSTRUCTION, A_INSTRUCTION -> lineNumber++;
				case L_INSTRUCTION -> symbolTable.addEntry(parser.symbol(), lineNumber+1);
			}
		}

		return symbolTable;
	}
}
