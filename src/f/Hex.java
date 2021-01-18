/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package f;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

import java.math.BigInteger;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Clrkz
 */
public class Hex {
    static final String ALGORITHM = "AES";
static String KEY = "2c0d1p8oc2d0p1o8";
    /*
    public static void main(String args[]) throws Exception{
        //System.out.println(toHex("WXeB9DSkLzGn6+QqL5UFNxCtM7wgSkl5f0sib3UWyz4="));
        
//System.out.println("DECODED BASE64: "+value);
       String str = "jdbc:mysql://localhost/cdpo_db";
       String eStr = encrypt(str); 
       String encHex = toHex(eStr);
        byte[] bytes = DatatypeConverter.parseHexBinary(encHex);
       String decHex = new String(bytes, "utf-8");
       String dStr = decrypt(eStr);
       
         System.out.println("ORIGINAL STRING: "+ str); 
         System.out.println("CIPHER ENCRYPT: "+ eStr); 
         System.out.println("BASE64 ENCRYPT: "+ encHex);  
         System.out.println("BASE64 DECRYPT: "+ decHex);  
         System.out.println("CIPHER DECRYPT: "+ dStr);
         
    }
    */
    public static String toHex(String value) throws Exception{ 
return String.format("%040x", new BigInteger(1, value.getBytes("utf-8"))); 
}
    
    
public static void clrkzPiracy(){
    byte[] pass = new byte[16];
pass[0] = (byte) 45;
pass[1] = (byte) 67;
pass[2] = (byte) 108;
pass[3] = (byte) 97;
pass[4] = (byte) 114;
pass[5] = (byte) 107;
pass[6] = (byte) 122;
pass[7] = (byte) 99;
pass[8] = (byte) 100;
pass[9] = (byte) 112;
pass[10] = (byte) 111;
pass[11] = (byte) 50;
pass[12] = (byte) 48;
pass[13] = (byte) 49;
pass[14] = (byte) 56;
pass[15] = (byte) 45;
KEY = new String(pass);
System.out.print(KEY);
}

public static String encrypt(String value) throws Exception
{ 
    clrkzPiracy();
Key key = generateKey();
Cipher cipher = Cipher.getInstance(AESCrypt.ALGORITHM);
cipher.init(Cipher.ENCRYPT_MODE, key);
byte [] encryptedByteValue = cipher.doFinal(value.getBytes("utf-8"));
String encryptedValue64 = new BASE64Encoder().encode(encryptedByteValue);
return encryptedValue64;

}

public static String decrypt(String value) throws Exception
{
    clrkzPiracy();
Key key = generateKey();
Cipher cipher = Cipher.getInstance(AESCrypt.ALGORITHM);
cipher.init(Cipher.DECRYPT_MODE, key);
byte [] decryptedValue64 = new BASE64Decoder().decodeBuffer(value);
byte [] decryptedByteValue = cipher.doFinal(decryptedValue64);
String decryptedValue = new String(decryptedByteValue,"utf-8");
return decryptedValue;

}

private static Key generateKey() throws Exception 
{
Key key = new SecretKeySpec(AESCrypt.KEY.getBytes(),AESCrypt.ALGORITHM);
return key;
}
}
