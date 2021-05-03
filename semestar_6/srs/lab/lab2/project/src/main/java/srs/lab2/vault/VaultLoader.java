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

public class VaultLoader {
	
	private static final Charset CS = StandardCharsets.UTF_8;
	private static final String SEP = "=";
	private static final String LINE_SEP = System.lineSeparator();
	
	public Vault load(Path file, PasswordHasher hasher) {
		
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
