package encryption;

/**
 * This class implements "DeeSee Chiffre" encryption i.e. a key value "n" is used to shift each
 * letter of a string by the corresponding value.
 * @author Andrew Belmonte Sato
 * 
 */
public class DeeSee_Chiffre {

	public static void main(String[] args) {
		//test implementation
		String name = "clark";
		int key = 5;
		
		String newName = encrypt(name, key);
		System.out.println(newName);
		
	}
	
	/**
	 * This method is used to encrypt a string by shifting each letter by a given key value. 
	 * The method only works with lowercase letters and spaces.  
	 * @param toEncrypt The string you want to encrpyt
	 * @param key The value which is used to shift each letter
	 * @return An encrypted string
	 */
	public static String encrypt(String toEncrypt, int key) {
		
		//store string in a character array by using byte representation to add the key
		char c[] = new char[toEncrypt.length()];
		byte b[] = new byte[toEncrypt.length()];
		
		//module 26 to avoid repetitions of the alphabet
		int newKey  = key%26; 
		
		for(int i = 0; i<toEncrypt.length(); i++) {
			b[i] = (byte)toEncrypt.charAt(i);
			b[i] = (byte) (b[i] + newKey);
			
			//if-case values come form the ascii table
			if(b[i]<123 && b[i]>96){ 	//normal case, value is in range
				c[i] = (char)b[i];
			}else if(b[i]>=123){  		//value too big but within byte range (128)
				byte temp = b[i];
				c[i] = (char) (temp-26);//remove 26 to start from the beginning of the alphabet
			}else if(b[i] == 32+newKey){//case for spaces
				byte temp = 32;
				c[i] = (char) temp;
			}

			/**
			 * use this to check output
			 */
			//System.out.println(c[i]);
		}
		String encryptedString = new String(c);
		return encryptedString;
	
	}

}
