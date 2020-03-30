package bo.com.reportate.utils;

import java.util.Base64;

public class StringUtil {

    public static boolean isEmptyOrNull(String value){
        if(value==null){
            return true;
        }
        return value.trim().equals("");
    }

    public static boolean isBlank(String value) {
        return isEmptyOrNull(value);
    }

    public static String trimUpperCase(String valor){
        if(valor==null){
            return null;
        }
        valor=valor.trim();
        valor=valor.toUpperCase();
        return valor;
    }

    public String encode(String txt) {
        String base64 = byteToBase64(txt.getBytes());
        System.out.println(base64);
        System.out.println(base64.substring(0, base64.length()-1));
        return base64.substring(0, base64.length()-1);
    }

    public static String[] decode(String base64) {
        base64 += "=";
        System.out.println(base64);
        String txt = new String(base64ToByte(base64));
        return txt.split("\\|");
    }

    public static String byteToBase64(byte[] byteArray){
        byte[] encoded = Base64.getEncoder().encode(byteArray);
        return new String(encoded);
    }

    public static byte[] base64ToByte(String base64){
        byte[] decoded = Base64.getDecoder().decode(base64);
        return decoded;
    }

}
