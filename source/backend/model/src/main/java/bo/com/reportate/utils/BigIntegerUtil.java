package bo.com.reportate.utils;

import java.math.BigInteger;

public class BigIntegerUtil {

    public static boolean isBigInteger(String valor){
        try {
            BigInteger valorBigInteger=new BigInteger(valor);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public static BigInteger toBigInteger(String valor){
        try {
            return new BigInteger(valor);
        }
        catch (Exception e){
//            System.out.println(Integer.MAX_VALUE);
//            e.printStackTrace();
            return null;
        }
    }

    public static String addZerosToTheLeft(BigInteger valor, Integer longitud){
        String longitudCadena = "%0Xd".replace("X",longitud.toString());
        return String.format(longitudCadena, valor);
    }
}
