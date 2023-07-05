import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException {
		if (args.length > 1) {
			throw new IllegalArgumentException("argument(filePath) is only one");
		}

		String filePath = args[0];
		Parser parser = new Parser(filePath);
		CodeWriter codeWriter = new CodeWriter(filePath);

		while (parser.hasMoreLines()) {
			parser.advance();

			switch (parser.commandType()) {
				case C_ARITHMETIC -> codeWriter.writeArithmetic(parser.arg1());
				case C_PUSH, C_POP -> codeWriter.writePushPop(parser.commandType(), parser.arg1(), parser.arg2());
			}
		}

		codeWriter.close();
	}
}
