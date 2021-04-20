package ui.loaders;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ui.Clause;

public class ClauseLoader {
	
	public static final Charset CS = StandardCharsets.UTF_8;
	private static final char COMMENT_SYMBOL = '#';
	
	private ClauseLoader() {}
	
	public static List<Clause> loadFromFile(Path file) {
		Objects.requireNonNull(file);
		
		List<Clause> clauses = new ArrayList<>();
		try (BufferedReader br = Files.newBufferedReader(file, CS)) {
			while(true) {
				String lineRead = br.readLine();
				if (lineRead == null)
					break;
				
				String line = lineRead.strip();
				if (line.isEmpty() || line.charAt(0) == COMMENT_SYMBOL)
					continue;
				
				Clause clause = Clause.fromString(line);
				clauses.add(clause);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return clauses;
	}
	
}
