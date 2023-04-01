package Utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.util.ArrayList;
import java.util.Arrays;

public class EncryptDecryptUtil {
    public static String encryptXOR(String message, String key){
        try {
            if (message==null || key==null ) return null;
            char[] keys=key.toCharArray();
            char[] mesg=message.toCharArray();
            int ml=mesg.length;
            int kl=keys.length;
            char[] newmsg=new char[ml];
            for (int i=0; i<ml; i++){
                newmsg[i]=(char)(mesg[i]^keys[i%kl]);
            }
            mesg=null;
            keys=null;
            return new String(new BASE64Encoder().encodeBuffer(new String(newmsg).getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decryptXOR(String message, String key){
        try {
            if (message==null || key==null ) return null;
            BASE64Decoder decoder = new BASE64Decoder();
            char[] keys=key.toCharArray();
            char[] mesg=new String(decoder.decodeBuffer(message)).toCharArray();
            int ml=mesg.length;
            int kl=keys.length;
            char[] newmsg=new char[ml];
            for (int i=0; i<ml; i++){
                newmsg[i]=(char)(mesg[i]^keys[i%kl]);
            }
            mesg=null; keys=null;
            return new String(newmsg);
        }
        catch ( Exception e ) {
            return null;
        }
    }

    public static void main(String[] args) {
        //String strToEncode = "S96121";
        ArrayList<String> strToEncode = new ArrayList<String>(Arrays.asList("S96121","S76480"));
        for (int i=0;i<strToEncode.size();i++){
            String strEncryptedString = encryptXOR(strToEncode.get(i), SeleniumHelper.key);
            System.out.println("Encrypted string: " + strEncryptedString);
            String decodedPwd = decryptXOR(strEncryptedString, SeleniumHelper.key);
            System.out.println("Decrypted string: " + decodedPwd);
        }


    }

}
