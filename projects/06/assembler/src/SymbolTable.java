import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
	Map<String, Integer> symbolTable;
	public SymbolTable() {
		symbolTable = new HashMap<>();
	}

	public void addEntry(String symbol, int address) {
		symbolTable.put(symbol, address);
	}

	public boolean contains(String symbol) {
		return symbolTable.containsKey(symbol);
	}

	public int getAddress(String symbol) {
		return symbolTable.get(symbol);
	}


}
