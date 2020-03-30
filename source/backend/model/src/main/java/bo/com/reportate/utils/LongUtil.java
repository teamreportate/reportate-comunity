package bo.com.reportate.utils;

public class LongUtil {

    public static boolean isNullOrZero(Long valor){
        if(valor==null){
            return true;
        }
        if(valor.equals(new Long("0"))){
            return true;
        }
        return false;
    }

    public static boolean isLong(String valor){
        try {
            Long valorLong=new Long(valor);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public static Long toLong(String valor){
        try {
            Long valorLong=new Long(valor);
            return valorLong;
        } catch (Exception e){
            return null;
        }
    }

    public static Long toLong(Integer valor) {
        try {
            if (valor == null)
                return null;

            Long valorInt = new Long(String.valueOf(valor));
            return valorInt;
        } catch (Exception e) {
            return null;
        }
    }

}
