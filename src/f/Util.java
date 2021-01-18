/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package f;

/**
 *
 * @author Clrkz
 */
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Util 
{
static final String ALGORITHM = "AES";
static String KEY = "2c0d1p8oc2d0p1o8";

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
//System.out.println("MASTER KEY: "+KEY);
}
/*
public static void main(String args[]) throws Exception{
    System.out.println(got("347948664964626c486e5331654c38445764746259466b4a2b725553526e786d6b5965755a624d6b306e773d"));
}
*/

public static String get(String value) throws Exception{
    clrkzPiracy();
    String val = value;
    byte[] bytes = DatatypeConverter.parseHexBinary(val);
    value = new String(bytes, "utf-8");
//System.out.println("DECODED BASE64: "+value);
Key key = generateKey();
Cipher cipher = Cipher.getInstance(Util.ALGORITHM);
cipher.init(Cipher.DECRYPT_MODE, key);
byte [] dv64 = new BASE64Decoder().decodeBuffer(value); 
byte [] dbv = cipher.doFinal(dv64);
String dv = new String(dbv,"utf-8");
//System.out.println("DECODED TEXT: "+ dv);
return dv;
}

public static String got(String value) throws Exception{
    clrkzPiracy();
    String val = value;
    byte[] bytes = DatatypeConverter.parseHexBinary(val);
    value = new String(bytes, "utf-8");
//System.out.println("DECODED BASE64: "+value);
Key key = generateKey();
Cipher cipher = Cipher.getInstance(Util.ALGORITHM);
cipher.init(Cipher.ENCRYPT_MODE, key);
byte [] dv64 = new BASE64Decoder().decodeBuffer(value); 
byte [] dbv = cipher.doFinal(dv64);
String dv = new String(dbv,"utf-8");
//System.out.println("DECODED TEXT: "+ dv);
return dv;
}

private static Key generateKey() throws Exception 
{
Key key = new SecretKeySpec(Util.KEY.getBytes(),Util.ALGORITHM);
return key;
}
}
