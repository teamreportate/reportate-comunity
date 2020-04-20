package bo.com.reportate.utils;

public class LongUtil {

    public static boolean isNullOrZero(Long valor){
        if(valor==null){
            return true;
        }
        return valor.equals(new Long("0"));
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
            return new Long(valor);
        } catch (Exception e){
            return null;
        }
    }

    public static Long toLong(Integer valor) {
        try {
            if (valor == null)
                return null;

            return new Long(String.valueOf(valor));
        } catch (Exception e) {
            return null;
        }
    }

}
