package ui.loaders;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import ui.StateCostPair;
import ui.StringState;
import ui.StringStateSearchProblem;
import ui.StringStateSuccessorFunction;
import ui.collections.MyHashSet;
import ui.collections.MySet;

public class StringStateSearchProblemLoader {
	
	
	private static final char COMMENT_SYMBOL = '#';
	private static final String SPACE_REGEX = "\\s+";
	private static final char COLON = ':';
	private static final String COMMA_STR = ",";
	
	private StringStateSearchProblemLoader() {}
	
	public static StringStateSearchProblem loadProblemFromFile(Path filePath) {
		StringState initialState = null;
		StringState[] goalStates = null;
		StringStateSuccessorFunction succ = new StringStateSuccessorFunction();
		
		try (BufferedReader br = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
			int loadStatus = 0; // 0=ucitavanje pocetnog stanja, 1=ucitavanje zavrsnih, 2=ucitavanje prijelaza
			
			while (true) {
				String lineRead = br.readLine();
				if (lineRead == null)
					break;
				
				lineRead = lineRead.strip();
				if (lineRead.isEmpty() || lineRead.charAt(0) == COMMENT_SYMBOL)
					continue;
				
				switch(loadStatus) {
					case 0 -> {
						initialState = new StringState(lineRead);
						loadStatus++;
					}
					case 1 -> {
						String[] goalStatesStrArr = lineRead.split(SPACE_REGEX);
						int len = goalStatesStrArr.length;
						goalStates = new StringState[len];
						for (int i=0; i<len; i++)
							goalStates[i] = new StringState(goalStatesStrArr[i]);
						loadStatus++;
					}
					case 2 -> {
						updateSuccessors(succ, lineRead);
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new StringStateSearchProblem(initialState, succ, goalStates);
	}
	
	private static void updateSuccessors(StringStateSuccessorFunction succ, String line) {
		int colonIdx = line.indexOf(COLON);
		String parentStr = line.substring(0, colonIdx);
		StringState parent = new StringState(parentStr);
		
		String rest = line.substring(colonIdx+1).strip();
		boolean isEmpty = rest.isEmpty();
		MySet<StateCostPair<StringState>> stateCostPairs = new MyHashSet<>();
		
		if (!isEmpty) {
			String[] stateCostPairsStr = rest.split(SPACE_REGEX);
			for (var p : stateCostPairsStr) {
				String[] spl = p.split(COMMA_STR);
				String stateStr = spl[0];
				double cost = Double.parseDouble(spl[1]);
				StateCostPair<StringState> pair = new StateCostPair<>(new StringState(stateStr), cost);
				stateCostPairs.add(pair);
			}
		}
		succ.defineSuccessors(parent, stateCostPairs);
	}
	
}
