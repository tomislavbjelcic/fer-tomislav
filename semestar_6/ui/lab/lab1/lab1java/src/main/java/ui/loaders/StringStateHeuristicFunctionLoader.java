package ui.loaders;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import ui.StringState;
import ui.StringStateHeuristicFunction;

public class StringStateHeuristicFunctionLoader {
	
	private static final char COMMENT_SYMBOL = '#';
	private static final String COLON_STR = ":";
	
	private StringStateHeuristicFunctionLoader() {}
	
	public static StringStateHeuristicFunction loadHeuristicFromFile(Path file) {
		StringStateHeuristicFunction h = new StringStateHeuristicFunction();
		
		try (BufferedReader br = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
			
			while(true) {
				String lineRead = br.readLine();
				if (lineRead == null)
					break;
				
				lineRead = lineRead.strip();
				if (lineRead.isEmpty() || lineRead.charAt(0) == COMMENT_SYMBOL)
					continue;
				
				String[] removeColon = lineRead.split(COLON_STR);
				StringState state = new StringState(removeColon[0].strip());
				double val = Double.parseDouble(removeColon[1].strip());
				h.defineHeuristic(state, val);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return h;
	}
	
}
