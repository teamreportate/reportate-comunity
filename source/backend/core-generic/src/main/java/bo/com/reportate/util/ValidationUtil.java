package bo.com.reportate.util;

import bo.com.reportate.exception.OperationException;
import bo.com.reportate.utils.BigDecimalUtil;
import bo.com.reportate.utils.FormatUtil;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * MC4
 * Santa Cruz - Bolivia
 * project: reportate
 * package: bo.com.reportate.utils
 * date:    4/26/2019
 * author:  mquispe
 **/
public class ValidationUtil {
    public static final String DECIMAL_FORMAT = "####################.#####";


    public static boolean isNullOrZero(Long value) {
        return value == null || value.equals(0L);
    }

    public static void throwExceptionRequiredIfNull(String name, Object value) throws OperationException {
        if (value == null) {
            throw new OperationException(FormatUtil.requerido(name));
        }
    }

    public static void throwExceptionRequiredIfBlank(String name, String value) throws OperationException {
        if (value == null || value.trim().length() == 0) {
            throw new OperationException(FormatUtil.requerido(name));
        }
    }

    public static void throwExceptionNotFoundIfNull(String name, Object value, String codigo) throws OperationException {
        if (value == null) {
            throw new OperationException("El campo '" + name + "' " + codigo + " no se encontro registrado");
        }
    }

    public static void throwExceptionIfListNotUniqueResult(String name, List values, String codigo) throws OperationException {
        if (values == null || values.size() == 0) {
            throw new OperationException("El campo '" + name + "' " + codigo + " no se encontro registrado");
        }
        if (values.size() > 1) {
            throw new OperationException("El campo '" + name + "' " + codigo + " se encuentra registrado mas de una vez");
        }
    }

    public static void throwExceptionIfListBlank(String name, List values, String codigo) throws OperationException {
        if (values == null || values.size() == 0) {
            throw new OperationException("El campo '" + name + "' " + codigo + " no se encontro registrado");
        }
    }

    public static void throwExceptionIfInvalidNumber(String name, Long value, boolean required, Long greaterThan) throws OperationException {
        throwExceptionIfInvalidNumber(name, value, required, greaterThan, null);
    }
    public static void throwExceptionIfInvalidNumber(String name, Long value, boolean required, Long greaterThan, Long lessThan) throws OperationException {
        if (!required && value == null)
            return;

        if (required && value == null)
            throw new OperationException("El campo '" + name + "' es requerido");

        if (value.compareTo(greaterThan) <= 0) { // value es menor o igual
            throw new OperationException("El campo '" + name + "' debe ser mayor que " + greaterThan);
        }

        if (lessThan != null && value.compareTo(lessThan) >= 0) { // value es mayor o igual
            throw new OperationException("El campo '" + name + "' debe ser menor que " + lessThan);
        }
    }


    public static void throwExceptionIfInvalidNumber(String name, Integer value, boolean required, Integer greaterThan) throws OperationException {
        throwExceptionIfInvalidNumber(name, value, required, greaterThan, null);
    }

    public static void throwExceptionIfInvalidNumber(String name, Integer value, boolean required, Integer greaterThan, Integer lessThan) throws OperationException {
        if (!required && value == null)
            return;

        if (required && value == null)
            throw new OperationException("El campo '" + name + "' es requerido");
        if (value.compareTo(greaterThan) < 0 ) { // value es menor o igual
            throw new OperationException("El campo '" + name + "' debe ser mayor que " + greaterThan);
        }

        if (lessThan != null && value.compareTo(lessThan) >= 0) { // value es mayor o igual
            throw new OperationException("El campo '" + name + "' debe ser menor que " + lessThan);
        }
    }

    public static void throwExceptionIfInvalidBigDecimal(String name, BigDecimal value, boolean required, BigDecimal greaterThan, int fractionDigits, int totalDigits) throws OperationException {
        if (!required && value == null)
            return;

        if (required && value == null)
            throw new OperationException("El campo '" + name + "' es requerido");

        if (value.compareTo(greaterThan) > 0) { // value es mayor a greaterThan
            if (BigDecimalUtil.getQuantityDecimals(value) > fractionDigits) {
                throw new OperationException("Para el campo '" + name + "' " + value + " la cantidad de decimales debe ser menor a " + (fractionDigits + 1));
            }
            if (BigDecimalUtil.getQuantityDigits(value) > totalDigits) {
                throw new OperationException("Para el campo '" + name + "' " + value + " la cantidad de dígitos debe ser menor a " + (totalDigits + 1));
            }
        } else if (value.compareTo(greaterThan) == 0) {
            throw new OperationException("El campo '" + name + "' debe ser mayor a " + greaterThan);
        } else {
            throw new OperationException("El campo '" + name + "' " + value + " debe ser mayor a " + greaterThan);
        }
    }

    public static void throwExceptionIfInvalidBigDecimal(String name, BigDecimal value, boolean required, BigDecimal greaterThan, int fractionDigits) throws OperationException {
        throwExceptionIfInvalidBigDecimal(name, value, required, greaterThan, false, fractionDigits);
    }

    public static void throwExceptionIfInvalidBigDecimal(String name, BigDecimal value, boolean required, BigDecimal greaterThan, boolean equalToGreaterThan, int fractionDigits) throws OperationException {
        if (!required && value == null)
            return;

        if (required && value == null)
            throw new OperationException("El campo '" + name + "' es requerido");

        if (value.compareTo(greaterThan) > 0) { // value es mayor a greaterThan
            if (BigDecimalUtil.getQuantityDecimals(value) > fractionDigits) {
                throw new OperationException("Para el campo '" + name + "' " + value + " la cantidad de decimales debe ser menor a " + (fractionDigits + 1));
            }

        } else if (value.compareTo(greaterThan) == 0) {
            if (!equalToGreaterThan)
                throw new OperationException("El campo '" + name + "' debe ser mayor a " + greaterThan);
        } else {
            throw new OperationException("El campo '" + name + "' " + value + " debe ser mayor a " + greaterThan);
        }
    }

    public static BigDecimal throwExceptionIfInvalidBigdecimal(String name, String value) {
        if (value == null || value.trim().length() == 0) {
            throw new OperationException(FormatUtil.requerido(name));
        }

        try {
            NumberFormat numberFormat = new DecimalFormat(DECIMAL_FORMAT);
            numberFormat.parse(value.trim());
            return new BigDecimal(value.trim());
        } catch (ParseException e) {
            throw new OperationException("No logro parcear a BigDecimal el campo '" + name + "'");
        }
    }

    public static void throwExceptionIfInvalidText(String name, String value, boolean required, int maxLength) throws OperationException {
        throwExceptionIfInvalidText(name, value, required, 0, maxLength);
    }

    public static void throwExceptionIfInvalidText(String name, String value, boolean required, int minLength, int maxLength) throws OperationException {
        if (!required && (value == null || value.trim().length() == 0)) {
            return;
        }
        if (required && (value == null || value.trim().length() == 0)) {
            throw new OperationException(FormatUtil.requerido(name));
        }

        if (minLength > 0 && value.trim().length() < minLength) {
            throw new OperationException("Para el campo '" + name + "' " + value + " la longitud mínima debe ser " + minLength);
        }
        if (value.trim().length() > maxLength) {
            throw new OperationException("Para el campo '" + name + "' " + value + " la longitud máxima debe ser " + maxLength);
        }
    }

    public static void throwExceptionIfInvalidEmail(String name, String value, boolean required) throws OperationException {
        if (!required && (value == null || value.trim().length() == 0)) {
            return;
        }
        if (required && (value == null || value.trim().length() == 0)) {
            throw new OperationException(FormatUtil.requerido(name));
        }

        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        String email = value.trim();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()){
            if (name == null || name.trim().length() == 0)
                throw  new OperationException("Formato de correo electrónico no válido");
            else
                throw  new OperationException("El campo '" + name + "' tiene un formato no válido");
        }
    }

    public static void main(String[] args) {
        BigDecimal n1 = new BigDecimal("0");
        BigDecimal n2 = new BigDecimal("0.00");
        BigDecimal n3 = new BigDecimal("0.0");
        BigDecimal n4 = new BigDecimal("0.0000");
        BigDecimal n5 = new BigDecimal("0.1");
        BigDecimal n6 = new BigDecimal("0.0001");

        System.out.println(n1 + "   equals: " + BigDecimalUtil.isZero(n1));
        System.out.println(n1 + "   equals: " + BigDecimalUtil.isZero(n2));
        System.out.println(n1 + "   equals: " + BigDecimalUtil.isZero(n3));
        System.out.println(n1 + "   equals: " + BigDecimalUtil.isZero(n4));
        System.out.println(n1 + "   equals: " + BigDecimalUtil.isZero(n5));
        System.out.println(n1 + "   equals: " + BigDecimalUtil.isZero(n6));

//        System.out.println(n1 + "   decimals: " + getQuantityDecimals(n1));
//        System.out.println(n2 + "   decimals: " + getQuantityDecimals(n2));
//        System.out.println(n3 + "   decimals: " + getQuantityDecimals(n3));
//        System.out.println(n4 + "   decimals: " + getQuantityDecimals(n4));
    }

}
