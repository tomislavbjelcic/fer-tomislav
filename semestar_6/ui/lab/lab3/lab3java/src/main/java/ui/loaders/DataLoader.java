package ui.loaders;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

import ui.Features;

public class DataLoader {
	
	private static final Charset CS = StandardCharsets.UTF_8;
	private static final String CSV_REGEX = ",";
	
	private final Supplier<Features> featFactory;
	
	public DataLoader(Supplier<Features> featFactory) {
		this.featFactory = Objects.requireNonNull(featFactory);
	}
	
	public LoadedData loadCsv(Path path) {
		
		LoadedData data = null;
		
		try(BufferedReader br = Files.newBufferedReader(path, CS)) {
			
			String firstLine = br.readLine();
			if (firstLine == null)
				return null;
			
			String stripped = firstLine.strip();
			if (stripped.isEmpty())
				return null;
			
			String[] splitted = stripped.split(CSV_REGEX);
			int len = splitted.length;
			if (len < 2)
				return null;
			
			Features feat = featFactory.get();
			for (int i=0; i<len; i++) {
				String f = splitted[i];
				
				if (i == len-1) {
					feat.setOutputType(f);
				} else
					feat.addFeature(f);
			}
			
			int featCount = feat.getFeatureList().size();
			data = new LoadedData();
			data.features = feat;
			data.dataUnits = new LinkedList<>();
			
			while(true) {
				String lineRead = br.readLine();
				if (lineRead == null)
					break;
				
				String line = lineRead.strip();
				if (line.isEmpty())
					continue;
				String[] split = line.split(CSV_REGEX);
				if (split.length != (featCount+1))
					throw new RuntimeException("Line: " + line + " value count mismatch. Should be " + (featCount+1));
				
				Map<String, String> vals = new HashMap<>();
				for (int i=0; i<featCount+1; i++) {
					String v = split[i];
					boolean b = i==featCount;
					String key = b ? feat.getOutputType() : feat.getFeature(i);
					if (b) {
						feat.addOutputValue(v);
					} else {
						feat.addValue(key, v);
					}
					
					vals.put(key, v);
				}
				
				data.dataUnits.add(vals);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		return data;
	}
	
	
}
