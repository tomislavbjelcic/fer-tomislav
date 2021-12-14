package kik.lab2;

import java.util.List;

import javax.crypto.Cipher;

public interface SymmetricKeyBlockCipher {
	
	
	public List<String> getRequiredFields();
	
	public SymmetricKeyBlockCipher instance();
	
	public int getBlockSize();
	
	public int getKeySize();
	public void setKeySize(int keySize);
	
	public String getAlgorithmName();
	
	public void setEncryptionMode(EncryptionMode mode);
	public EncryptionMode getEncryptionMode();
	
	public default Cipher getCipherObject(int mode) throws Exception {
		String algo = getAlgorithmName();
		EncryptionMode encmode = getEncryptionMode();
		String modestr = encmode.toString();
		String paddingstr = encmode.getDefaultPadding();
		String transformation = algo + '/' + modestr + '/' + paddingstr;
		
		Cipher cipher = Cipher.getInstance(transformation);
		return null;
	}
	
}
