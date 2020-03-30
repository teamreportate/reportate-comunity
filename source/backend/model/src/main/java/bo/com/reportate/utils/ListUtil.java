package bo.com.reportate.utils;

import java.util.List;

/**
 * MC4
 * Santa Cruz - Bolivia
 * project: reportate
 * package: bo.com.reportate.utils
 * date:    5/16/2019
 * author:  Toshiba
 **/
public class ListUtil {

    public static boolean isEmptyOrNull(List list){
        if(list == null){
            return true;
        }
        if(list.isEmpty()){
            return true;
        }
        return false;
    }

}
