package vip.e7du.dc.api.idc.kits;

import com.jfinal.plugin.activerecord.Consts;

/**
 * Sort.DESC(column) <br/>
 * Sort.ASC(column) <br/>
 * 
 * @author Jobsz
 */
public class Sort {

    private Sort() {
    }

    public static String DESC(String column) {
        return (column + Consts.ORDER_DESC);
    }

    public static String ASC(String column) {
        return (column + Consts.ORDER_ASC);
    }
}
