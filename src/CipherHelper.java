package src;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
//import java.util.Base64;
import org.apache.commons.codec.binary.Base64;

class CipherHelper {

    static String encrypt(String originalSource, String secretKey, String algorithm)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException {

        byte[] originalBytes = originalSource.getBytes();
        byte[] encryptBytes = cipher(true, originalBytes, secretKey, algorithm);

        //内蔵の暗号化・復号化ライブラリで使う
        //byte[] encryptBytesBase64 = Base64.getEncoder().encode(encryptBytes);
        byte[] encryptBytesBase64 = Base64.encodeBase64(encryptBytes);
        return new String(encryptBytesBase64);
    }


    static String decrypt(String encryptBytesBase64String, String secretKey, String algorithm)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException {

        //内蔵の暗号化・復号化ライブラリで使う
        //byte[] encryptBytes = Base64.getDecoder().decode(encryptBytesBase64String);
        byte[] encryptBytes = Base64.decodeBase64(encryptBytesBase64String);
        byte[] originalBytes = cipher(false, encryptBytes, secretKey, algorithm);
        return new String(originalBytes);
    }

    private static byte[] cipher(boolean isEncrypt, byte[] source, String secretKey, String algorithm) throws InvalidKeyException,
            NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        byte[] secretKeyBytes = secretKey.getBytes();

        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyBytes, algorithm);
        Cipher cipher = Cipher.getInstance(algorithm);
        if (isEncrypt) {
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        } else {
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        }

        return cipher.doFinal(source);
    }
}