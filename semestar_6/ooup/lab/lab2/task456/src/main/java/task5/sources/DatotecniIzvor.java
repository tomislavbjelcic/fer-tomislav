package task5.sources;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import task5.IzvorBrojeva;

public class DatotecniIzvor implements IzvorBrojeva {
	
	private BufferedReader br;
	
	public DatotecniIzvor(Path file) throws IOException {
		Objects.requireNonNull(file);
		br = Files.newBufferedReader(file, StandardCharsets.UTF_8);
	}
	
	public DatotecniIzvor(String pathStr) throws IOException {
		this(Paths.get(Objects.requireNonNull(pathStr)));
	}

	@Override
	public void close() throws IOException {
		br.close();
	}

	@Override
	public int getNext() throws IOException {
		String lineRead = br.readLine();
		if (lineRead == null)
			return -1;
		int parsed = -1;
		try {
			parsed = Integer.parseInt(lineRead);
		} catch (NumberFormatException ex) {} 
		return parsed;
	}

}
