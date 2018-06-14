package vip.e7du.jfm.testing;

import org.junit.Test;

import com.jfinal.plugin.activerecord.Consts;

import vip.e7du.dc.api.idc.kits.Sort;
import vip.e7du.dc.api.idc.kits.SqlKit;
import vip.e7du.dc.api.idc.kits.SqlKit.Sql;
import vip.e7du.subsystem.api.model.FireCity;

public class OrderTest {

    @Test
    public void test() {
        FireCity c = new FireCity();
        c.setAreaCode("code");
        c.setOrders(Sort.DESC(FireCity.AREA_CODE), Sort.DESC(FireCity.CREATE_DATE));

        Sql sql = SqlKit.query(c.dcExport().get(Consts.ATTR_KEY));
        String[] orders = (String[]) c.dcExport().get(Consts.ORDER_KEY);
        System.out.println("sql ==" + sql.orderBy(orders));
    }

    @Test
    public void testParserOrder() {
        String order = Sort.ASC(FireCity.CREATE_DATE);

        System.out.println(order);

        String[] orders = order.split(Consts.ORDER_ASC.trim());
        String column = "";
        if (orders.length == 2) {
            column = orders[0].trim();
        }
        System.out.println(column);
        System.out.println("this column prepare for cache");
    }
}
