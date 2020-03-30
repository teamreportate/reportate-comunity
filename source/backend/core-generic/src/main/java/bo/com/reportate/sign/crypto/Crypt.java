package bo.com.reportate.sign.crypto;

/**
 * Clase que genera la encriptacion de datos
 *
 * @author Ricardo Laredo
 */
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Random;

public class Crypt {

    private final Random secureRandom;
    private Base64 base64;
    private static Crypt crypt = new Crypt();

    private Crypt() {
        base64 = new Base64(true);
        secureRandom = new SecureRandom();
    }

    public static Crypt getInstance() {
        return crypt;
    }

    public String crypt(String textPlain, String llave) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKeySpec key = new SecretKeySpec(base64.decode(llave), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return base64.encodeAsString(cipher.doFinal(textPlain.getBytes(StandardCharsets.UTF_8)));
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    public String crypt(String textPlain) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            String llave = this.generateKey();
            SecretKeySpec key = new SecretKeySpec(base64.decode(llave), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            String ciphered = base64.encodeAsString(cipher.doFinal(textPlain.getBytes(StandardCharsets.UTF_8)));
            return llave + ciphered;
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    public String decrypt(String cipheredInBase64) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            String llave = cipheredInBase64.substring(0, 22);
            cipheredInBase64 = cipheredInBase64.substring(22);
            SecretKeySpec key = new SecretKeySpec(base64.decode(llave), "AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decode = base64.decode(cipheredInBase64);
            byte[] ciphered = cipher.doFinal(decode);
            return new String(ciphered, StandardCharsets.UTF_8);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    public String decrypt(String cipheredInBase64, String llave) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            SecretKeySpec key = new SecretKeySpec(base64.decode(llave), "AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decode = base64.decode(cipheredInBase64);
            byte[] ciphered = cipher.doFinal(decode);
            return new String(ciphered, StandardCharsets.UTF_8);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateKey() {
        byte[] key = new byte[16];
        secureRandom.nextBytes(key);
        return new Base64(true).encodeAsString(key);
    }

    public String generateKey(int nChars) {
        int bytes = (int) ((double) (nChars * 6) / 8 + 0.49);
        byte[] key = new byte[bytes];
        secureRandom.nextBytes(key);
        return new Base64(true).encodeAsString(key);
    }

    public static void main(String arg[]){
        String s = Crypt.getInstance().crypt("Dinamicina01");
        System.out.println(s);
        System.out.println(Crypt.getInstance().decrypt(s));
    }

}
