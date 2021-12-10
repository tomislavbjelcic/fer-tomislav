package kik.lab2;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

public class Main {
	
	public static void main(String[] args) {
		
		String str = "prižma";
		Path file = Path.of("out.txt");
		Charset cs = StandardCharsets.UTF_16LE;
		
		try {
			Files.writeString(file, str, cs);
			System.out.println("Done.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		byte[] content = null;
		try {
			content = Files.readAllBytes(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		var enc = Base64.getEncoder();
		String s = enc.encodeToString(content);
		System.out.println(s);
		
	}
	
}
