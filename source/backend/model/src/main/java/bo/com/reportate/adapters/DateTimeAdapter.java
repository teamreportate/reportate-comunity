package bo.com.reportate.adapters;

import bo.com.reportate.utils.DateUtil;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Date;

public class DateTimeAdapter extends XmlAdapter<String, Date> {

    @Override
    public String marshal(Date date) {
        return DateUtil.toString(DateUtil.FORMAT_FACTURA,date);
    }

    @Override
    public Date unmarshal(String date) {
        return DateUtil.toDate(DateUtil.FORMAT_FACTURA,date);
    }
}
