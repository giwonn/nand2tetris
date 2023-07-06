import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException {
		if (args.length > 1) {
			throw new IllegalArgumentException("Only one file path can be entered");
		} else if (args.length == 0) {
			throw new IllegalArgumentException("Please enter the file path");
		}

		Parser parser = new Parser(args[0]);
		CodeWriter codeWriter = new CodeWriter(args[0]);

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
