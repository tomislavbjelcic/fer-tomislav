package kik.lab2;

import java.util.List;

public interface SymmetricKeyBlockCipher {
	
	
	public List<String> getRequiredFields();
	
	public SymmetricKeyBlockCipher instance();
	
	public int getBlockSize();
	
	public int getKeySize();
	public int setKeySize(int keySize);
	
	public void setAlgorithm(String algorithm);
	
	public String setMode(String mode);
	
}
