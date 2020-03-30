package bo.com.reportate.utils;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
public class BigDecimalUtil {

    private static final int QUANTITY_DECIMAL = 5;// Max 5 decimales, Operaciones
    public static final int QUANTITY_DECIMAL_SHOW = 2;

    public static BigDecimal toBigDecimal(String valor) {
        try {
            return new BigDecimal(valor);
        } catch (Exception e) {
           log.error("error",e);
            return null;
        }
    }

    public static boolean isZero(BigDecimal valor) {
        return valor != null && valor.compareTo(new BigDecimal("0")) == 0;
    }

    public static boolean isNullOrZero(BigDecimal valor) {
        if (valor == null) {
            return true;
        }
        return valor.compareTo(new BigDecimal("0")) == 0;
    }

    public static BigDecimal plus(BigDecimal numberA, BigDecimal numberB) {
        return numberA.add(numberB);
    }

    public static boolean equals(BigDecimal numberA, BigDecimal numberB) {
        if (numberA == null) {
            return numberB == null;
        }
        if (numberB == null)
            return false;
        return numberA.compareTo(numberB) == 0;
    }

    public static BigDecimal roundTo(BigDecimal numberA, Integer quantityDecimals){
        return numberA.setScale(quantityDecimals, RoundingMode.HALF_UP);
    }

    public static BigDecimal truncateTo(BigDecimal numberA, Integer quantityDecimals){
        return numberA.setScale(quantityDecimals, RoundingMode.DOWN);
    }

    public static BigDecimal subtract(BigDecimal numberA, BigDecimal numberB) {
        return numberA.subtract(numberB);
    }

    public static BigDecimal multiply(BigDecimal numberA, BigDecimal numberB) {
        return numberA.multiply(numberB);
    }

    public static BigDecimal divide(BigDecimal numberA, BigDecimal numberB) {
        BigDecimal result = numberA.divide(numberB, QUANTITY_DECIMAL + 2, RoundingMode.HALF_UP);
        result = result.stripTrailingZeros();
        return result;
    }

    public static BigDecimal divideAndRound(BigDecimal numberA, Integer quantityDecimals, BigDecimal numberB) {
        return numberA.divide(numberB, quantityDecimals, RoundingMode.HALF_UP);
    }

    public static int getQuantityDecimals(BigDecimal bigDecimal) {
        String string = bigDecimal.toString();
        int index = string.indexOf(".");
        return index < 0 ? 0 : string.length() - index - 1;
    }

    public static int getQuantityDigits(BigDecimal bigDecimal) {
        String string = bigDecimal.toString();
        int index = string.indexOf(".");
        int qDecimal = index < 0 ? 0 : string.length() - index - 1;
        int qPart = index < 0 ? string.length() : index;
        return qPart + qDecimal;
    }

    public static BigDecimal defaultIsNull(BigDecimal value, BigDecimal defaultValue) {
        if (value == null)
            return defaultValue;
        return value;
    }

}
