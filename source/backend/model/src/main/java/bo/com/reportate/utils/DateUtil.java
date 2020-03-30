package bo.com.reportate.utils;

import lombok.extern.slf4j.Slf4j;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

@Slf4j
public class DateUtil {
    public static final String FORMAT_FECHA_UTC = "yyyy-MM-dd'T'HH:mm:ss.SSS"; // Definido por el SIN
    public static final String FORMAT_FACTURA = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    public static final String FORMAT_FACTURA_CUF = "yyyyMMddHHmmssSSS";
    public static final String FORMAT_FILE = "yyyy-MM-dd HHmmss";
    public static final String FORMAT_FECHA_ESTADISTICA = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_DATE_TIME = "dd/MM/yyyy HH:mm:ss";
    public static final String FORMAT_DATE = "dd/MM/yyyy";
    public static final String FORMAT_TIME = "HH:mm:ss";
    public static final String FORMAT_DATE_PARAM_URL = "dd-MM-yyyy";

    public static final String FORMAT_YYYYMMDDHHmmss = "yyyy-MM-dd HH:mm:ss";

    public static Date getLastDateOfMonth(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    public static Date getFirstDateOfMonth(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    public static Date formatToStart(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTime();
    }

    public static Date formatToEnd(Date date)  {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        calendar.set(Calendar.MILLISECOND,999);
        return calendar.getTime();
    }

    public static Date toDate(String format, String valor) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(valor);
        } catch (Exception ex) {
            log.error("Error", ex);
            return null;
        }
    }

    public static String toString(String format, Date valor) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(valor);
        } catch (Exception ex) {
            log.error("Error", ex);
            return null;
        }
    }

    public static boolean equals(Date date1, Date date2, String format){
        if(date1 == null && date2 == null){
            return true;
        }else if(date1 != null && date2 != null){
            String date1Str = toString(format, date1);
            String date2Str = toString(format, date2);
            if(date1Str == null) {
                return false;
            }
            return date1Str.equals(date2Str);
        }
        return false;
    }

    /**
     * Convierte Date a XMLGregorianCalendar
     */
    private static XMLGregorianCalendar toXMLGregorianCalendar(Date date) {

        XMLGregorianCalendar xmlCalendar = null;
        try {
            if (date != null) {
                GregorianCalendar gCalendar = new GregorianCalendar();
                gCalendar.setTime(date);
                xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gCalendar);
            }
        } catch (DatatypeConfigurationException ex) {
            log.error("Error", ex);
        }
        return xmlCalendar;
    }

    /**
     * Convierte DateString a XMLGregorianCalendar
     */
    public static XMLGregorianCalendar toXMLGregorianCalendar(String format, String valor) {
        return toXMLGregorianCalendar(DateUtil.toDate(format, valor));
    }

    /**
     * Convierte XMLGregorianCalendar a Date
     */
    public static Date toDate(XMLGregorianCalendar calendar) {
        return calendar == null ? null : calendar.toGregorianCalendar().getTime();
    }

    public static Date plus(Date date, int hoursToAdd, int minutesToAdd, int secondsToAdd) {
        Calendar calDateStart = Calendar.getInstance();
        calDateStart.setTime(date);
        calDateStart.add(Calendar.HOUR, hoursToAdd);
        calDateStart.add(Calendar.MINUTE, minutesToAdd);
        calDateStart.add(Calendar.SECOND, secondsToAdd);

        return calDateStart.getTime();
    }

    public static Date plusMilliseconds(Date date, int millisecondsToAdd) {
        Calendar calDateStart = Calendar.getInstance();
        calDateStart.setTime(date);
        calDateStart.add(Calendar.MILLISECOND, millisecondsToAdd);

        return calDateStart.getTime();
    }

    public static Date plusSeconds(Date date, int secondsToAdd) {
        Calendar calDateStart = Calendar.getInstance();
        calDateStart.setTime(date);
        calDateStart.add(Calendar.SECOND, secondsToAdd);

        return calDateStart.getTime();
    }

    public static Date plusMinutes(Date date, int minutesToAdd) {
        Calendar calDateStart = Calendar.getInstance();
        calDateStart.setTime(date);
        calDateStart.add(Calendar.MINUTE, minutesToAdd);

        return calDateStart.getTime();
    }

    public static Date defaultIsNull(Date date, Date defaultDate) {
        return date == null ? defaultDate : date;
    }


    public static long diferenciaDias(Date fechaInicial, Date fechaFin) {
        long diffInMillies = Math.abs(fechaFin.getTime() - fechaInicial.getTime());
        return TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    public static long diferenciaHoras(Date fechaInicial, Date fechaFin) {
        long diffInMillies = Math.abs(fechaFin.getTime() - fechaInicial.getTime());
        return TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    public static Date adicionarDias(Date date, int days) {
        Calendar calDateStart = Calendar.getInstance();
        calDateStart.setTime(date);
        calDateStart.add(Calendar.DATE, days);
        return calDateStart.getTime();
    }

    public static Date adicionarAnios(Date date, int anios) {
        Calendar calDateStart = Calendar.getInstance();
        calDateStart.setTime(date);
        calDateStart.add(Calendar.YEAR, anios);
        return calDateStart.getTime();
    }
}
