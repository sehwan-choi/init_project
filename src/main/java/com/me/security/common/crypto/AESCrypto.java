package com.me.security.common.crypto;

import com.me.security.common.crypto.exception.CryptoException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@RequiredArgsConstructor
public class AESCrypto {

    private final String aesKey;

    /**
     * AES256 암호화
     * @param raw 암호화 되지않은 데이터
     * @return AES256 암호화 된 데이터
     * @exception com.me.security.common.crypto.exception.CryptoException AES256 암호화중 예외 발생시 Exception 발생
     */
    public String encrypt(String raw) {
        if (raw == null || raw.isBlank() || raw.isEmpty()) {
            log.warn("AESHexEncrypt input raw text is empty(blank) or null");
            return "";
        }
        try {
            byte[] keyBytes = Hex.decodeHex(aesKey.toCharArray());

            SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

            byte[] encrypted = cipher.doFinal(raw.getBytes());

            StringBuilder sb = new StringBuilder(encrypted.length * 2);
            String hexNumber;

            for (byte b : encrypted) {
                hexNumber = "0" + Integer.toHexString(0xff & b);
                sb.append(hexNumber.substring(hexNumber.length() - 2));
            }

            return sb.toString();
        } catch (DecoderException | NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e) {
            throw new CryptoException("AESHexEncrypt throws exception " + e.getMessage(), e);
        }
    }

    /**
     * AES256 복호화
     * @param encodedText 암호화 된 데이터
     * @return AES256 복호화 된 데이터
     * @exception CryptoException AES256 복호화중 예외 발생시 Exception 발생
     */
    public String decrypt(String encodedText) {
        if (encodedText == null || encodedText.isEmpty() || encodedText.isBlank()) {
            log.warn("AESHexDecrypt input encoded text is empty(blank) or null");
            return "";
        }
        try {
            byte[] keyBytes = Hex.decodeHex(aesKey.toCharArray());

            SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

            //16진수 문자열을 byte로 변환
            byte[] byteArray = new byte[encodedText.length() /2 ];

            for(int i=0; i<byteArray.length; i++){
                byteArray[i] = (byte) Integer.parseInt(encodedText.substring(2 * i, 2*i+2), 16);
            }

            byte[] original = cipher.doFinal(byteArray);

            return new String(original);
        } catch (DecoderException | NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException e) {
            throw new CryptoException("AESHexDecrypt throws exception " + e.getMessage(), e);
        }
    }
}
