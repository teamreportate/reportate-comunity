package bo.com.reportate.utils;

public class IntegerUtil {

    public static boolean isNullOrZero(Integer valor) {
        if (valor == null) {
            return true;
        }
        if (valor.equals(new Integer("0"))) {
            return true;
        }
        return false;
    }

    public static Integer toInteger(String valor) {
        try {
            if (valor == null || valor.trim().length() == 0)
                return null;

            Integer valorInt = new Integer(valor);
            return valorInt;
        } catch (Exception e) {
            return null;
        }
    }

    public static Integer toInteger(Long valor) {
        try {
            if (valor == null)
                return null;

            Integer valorInt = new Integer(String.valueOf(valor));
            return valorInt;
        } catch (Exception e) {
            return null;
        }
    }

    public static Integer defaultIsNullOrZero(Integer value, Integer defaultValue) {
        if (value == null || value == 0) {
            return defaultValue;
        } else {
            return value;
        }
    }
}
