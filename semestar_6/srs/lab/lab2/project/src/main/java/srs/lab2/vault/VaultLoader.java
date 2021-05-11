package srs.lab2.vault;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import srs.lab2.pw.PasswordHasher;

/**
 * Objekt koji može čitati spremnik podataka iz datoteke.
 * 
 * @author tomislav
 *
 */
public class VaultLoader {
	
	/**
	 * Kodna stranica koja se koristi.
	 */
	private static final Charset CS = StandardCharsets.UTF_8;
	/**
	 * Separator između zapisa korisničkog imena i podataka o tom korisničkom imenu.
	 */
	private static final String SEP = "=";
	/**
	 * Separator između linija specifičan za operacijski sustav na kojem se program izvodi.
	 */
	private static final String LINE_SEP = System.lineSeparator();
	
	/**
	 * Provjerava postoji li već spremnik na disku na putanji {@code file}.
	 * 
	 * @param file
	 * @return {@code true} ako postoji, {@code false} inače.
	 */
	private static boolean checkVault(Path file) {
		return Files.exists(file) && Files.isRegularFile(file);
	}
	
	/**
	 * Učitava spremnik iz datoteke na putanju {@code file} i inicijalizira objekt {@link Vault}
	 * da koristi funkciju sažetka {@code hasher}.<br>
	 * Ako spremnik ne postoji na putanji {@code file}, metoda vraća {@code null}.
	 * 
	 * @param file
	 * @param hasher
	 * @return
	 */
	public Vault load(Path file, PasswordHasher hasher) {
		if (!checkVault(file))
			return null;
		
		Map<String, UserInfo> vaultMap = new HashMap<>();
		try (BufferedReader br = Files.newBufferedReader(file, CS)) {
			while(true) {
				String lineRead = br.readLine();
				if (lineRead == null)
					break;
				
				String line = lineRead.strip();
				String[] splitted = line.split(SEP);
				
				String userName = splitted[0];
				UserInfo uinfo = UserInfo.parse(splitted[1]);
				vaultMap.put(userName, uinfo);
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Vault v = new Vault(hasher, vaultMap);
		return v;
	}
	
	/**
	 * Spremnik {@code v} zapisuje na disk na putanji {@code file}.
	 * 
	 * @param file
	 * @param v
	 */
	public void save(Path file, Vault v) {
		var map = v.getVaultMap();
		
		try (BufferedWriter bw = Files.newBufferedWriter(file, CS)) {
			for (var entry : map.entrySet()) {
				String userName = entry.getKey();
				UserInfo uinfo = entry.getValue();
				String uinfoStr = uinfo.toString();
				
				String write = userName + SEP + uinfoStr + LINE_SEP;
				bw.write(write);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
