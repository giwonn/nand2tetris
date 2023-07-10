import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) throws IOException {
		if (args.length > 1) {
			throw new IllegalArgumentException("Only one path can be entered");
		} else if (args.length == 0) {
			throw new IllegalArgumentException("Please enter the path");
		}

		List<File> files = new ArrayList<>();


		File input = new File(args[0]);
		getFiles(input, files);


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

	private static void getFiles(File input, List<File> files) {
		if (input.isFile()) {
			String fileName = input.getName();
			if (fileName.contains(".vm")) {
				files.add(input);
			}
		} else if (input.isDirectory()) {
			File[] innerFiles = input.listFiles();
			if (innerFiles == null) return;
			for (File f: innerFiles) {
				getFiles(f, files);
			}
		}
	}
}
