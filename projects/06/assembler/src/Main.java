import javax.security.auth.callback.Callback;
import java.io.*;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		System.out.print("Please enter the path of the file to run: ");
		String filePath = sc.nextLine();
		sc.close();

			System.out.println("convert to binary process START: " + filePath);
			try {
					if (!filePath.endsWith(".asm")) {
						throw new FileNotFoundException();
					}

					File file = new File(filePath);
					SymbolTable symbolTable = createSymbolTable(file);

					// parser, code 모듈 초기화
					Parser parser = new Parser(new BufferedReader(new FileReader(file)));
					Code code = new Code();

					// 변환한 기계어 저장할 파일 및 스트림 생성
					String hackFilePath = file.getCanonicalPath().replace(".asm", ".hack");
					File hackFile = new File(hackFilePath);
					BufferedWriter writer = new BufferedWriter(new FileWriter(hackFile));

					// SymbolTable에 할당되지 않은 변수는 16번 메모리부터 할당 (15번까지는 예약됨)
					int variableAddress = 16;

					// 파일의 끝까지 읽음
					while(parser.hasMoreLines()) {
						// 다음 라인으로 이동
						parser.advance();
						// 주석이거나 빈 라인인 경우 스킵
						if (parser.currentLine.equals("")) continue;

						// C-명령어인 경우
						if (parser.instructionType() == InstructionType.C_INSTRUCTION) {
							// 어셈블리어를 comp, dest, jump로 파싱하여 저장
							writer.write("111" + code.comp(parser.comp()) + code.dest(parser.dest()) + code.jump(parser.jump()) + "\n");
						}

						// A-명령어인 경우
						if (parser.instructionType() == InstructionType.A_INSTRUCTION) {
							String symbol = parser.symbol();
							String address;

							// symbol이 숫자일 경우
							if (symbolIsNumber(symbol)) {
								// 2진수로 변환
								address = Integer.toBinaryString(Integer.parseInt(symbol));
							} else if (symbolTable.contains(symbol)) { // 문자일 경우 && symbolTable에 존재할 경우
								// symbolTable에서 주소를 가져와 2진수로 변환
								address = Integer.toBinaryString(symbolTable.getAddress(symbol));
							} else {
								// symbolTable에 존재하지 않을 경우 symbolTable에 추가하고 2진수로 변환
								symbolTable.addEntry(symbol, variableAddress);
								address = Integer.toBinaryString(variableAddress);
								variableAddress++;
							}
							// 변환한 2진수를 16비트로 변환 후 저장
							String address16Bit = String.format("%016d", Long.parseLong(address));
							writer.write(address16Bit + "\n");
						}
					}

					writer.flush();
					writer.close();
					System.out.println("convert to binary process SUCCESS: " + hackFile.getName());
			} catch (FileNotFoundException e) {
				if (filePath.endsWith(".asm")) {
					e.printStackTrace();
				}
				System.out.println("File extension is not .asm");
			} catch (IOException e) {
				throw new RuntimeException(e);
			} finally {
				System.out.println("convert to binary process END: " + filePath);
			}
	}

	static SymbolTable createSymbolTable(File file) throws IOException {
		Reader f = new FileReader(file);
		Parser parser = new Parser(new BufferedReader(new FileReader(file)));
		SymbolTable symbolTable = new SymbolTable();
		int lineNumber = 0;

		while(parser.hasMoreLines()) {
			parser.advance();
			if (parser.currentLine.equals("")) continue;

			switch(parser.instructionType()) {
				case C_INSTRUCTION, A_INSTRUCTION -> lineNumber++;
				case L_INSTRUCTION -> {
					if (symbolTable.contains(parser.symbol())) {
						lineNumber++;
					} else {
						symbolTable.addEntry(parser.symbol(), lineNumber);
					}
				}
			}
		}

		return symbolTable;
	}

	static boolean symbolIsNumber(String symbol) {
		return symbol.chars().allMatch(Character::isDigit);
	}
}
