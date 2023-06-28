public class Code {

	String dest(String dest) {
		return switch(dest) {
			case "" -> "000";
			case "M" -> "001";
			case "D" -> "010";
			case "DM", "MD" -> "011";
			case "A" -> "100";
			case "AM", "MA" -> "101";
			case "AD", "DA" -> "110";
			case "ADM", "AMD", "DAM", "DMA", "MAD", "MDA" -> "111";
			default -> throw new Error("invalid dest.");
		};
	}

	String comp(String comp) {
		String a = comp.contains("M") ? "1" : "0";
		return a + switch(comp) {
			case "0" -> "101010";
			case "1" -> "111111";
			case "-1" -> "111010";
			case "D" -> "001100";
			case "A", "M" -> "110000";
			case "!D" -> "001101";
			case "!A", "!M" -> "110001";
			case "-D" -> "001111";
			case "-A", "-M" -> "110011";
			case "D+1", "1+D" -> "011111";
			case "A+1", "1+A", "M+1", "1+M" -> "110111";
			case "D-1", "-1+D" -> "001110";
			case "A-1", "-1+A", "M-1", "-1+M" -> "110010";
			case "D+A", "A+D", "D+M", "M+D" -> "000010";
			case "D-A", "-A+D", "D-M", "-M+D" -> "010011";
			case "A-D", "-D+A", "M-D", "-D+M" -> "000111";
			case "D&A", "A&D", "D&M", "M&D" -> "000000";
			case "D|A", "A|D", "D|M", "M|D" -> "010101";
			default -> throw new Error("invalid comp.");
		};
	}

	String jump(String jump) {
		return switch(jump) {
			case "" -> "000";
			case "JGT" -> "001";
			case "JEQ" -> "010";
			case "JGE" -> "011";
			case "JLT" -> "100";
			case "JNE" -> "101";
			case "JLE" -> "110";
			case "JMP" -> "111";
			default -> throw new Error("invalid jump.");
		};
	}
}
