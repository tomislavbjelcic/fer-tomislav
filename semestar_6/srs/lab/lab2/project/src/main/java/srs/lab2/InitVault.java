package srs.lab2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class InitVault {
	
	public static void main(String[] args) {
		Path p = Common.VAULT_PATH.toAbsolutePath().normalize();
		if (Files.exists(p)) {
			System.out.println("Vault at path " + p + " already exists.");
			return;
		}
		
		Path parent = p.getParent();
		if (parent != null && !Files.exists(parent)) {
			try {
				Files.createDirectories(parent);
				System.out.println("Created directories: " + parent);
			} catch (IOException e) {
				System.out.println("Unable to create directory " + parent);
				return;
			}
		}
		
		try {
			Files.createFile(p);
			System.out.println("Created empty vault at path: " + p);
		} catch (IOException e) {
			System.out.println("Unable to create vault at path " + p);
			return;
		}
		
	}
	
}
