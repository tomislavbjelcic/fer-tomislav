package task5.listeners;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Objects;

import task5.CollectionListener;

public class FileWriteListener implements CollectionListener {
	
	private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	private static final char NL = '\n';
	
	private Path file;
	
	public FileWriteListener(Path file) {
		this.file = Objects.requireNonNull(file);
	}
	
	public FileWriteListener(String pathStr) {
		this(Paths.get(Objects.requireNonNull(pathStr)));
	}

	@Override
	public void collectionChanged(Collection<Integer> col) {
		try (BufferedWriter bw = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
			for (Integer i : col) {
				bw.write(i.toString() + NL);
			}
			
			var now = LocalDateTime.now();
			bw.write(NL);
			String datetime = DTF.format(now);
			bw.write(datetime + NL);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
