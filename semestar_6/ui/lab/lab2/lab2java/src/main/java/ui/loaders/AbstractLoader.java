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

public abstract class AbstractLoader<T> {
	
	private static final Charset CS = StandardCharsets.UTF_8;
	private static final char COMMENT_SYMBOL = '#';
	
	public List<T> loadFromFile(Path file) {
		Objects.requireNonNull(file);
		
		List<T> clauses = new ArrayList<>();
		try (BufferedReader br = Files.newBufferedReader(file, CS)) {
			while(true) {
				String lineRead = br.readLine();
				if (lineRead == null)
					break;
				
				String line = lineRead.strip();
				if (line.isEmpty() || line.charAt(0) == COMMENT_SYMBOL)
					continue;
				
				T loaded = load(line);
				clauses.add(loaded);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return clauses;
	}
	
	protected abstract T load(String str);
	
}
