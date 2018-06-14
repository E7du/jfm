/**
 * 
 */
package vip.e7du.jfm.testing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Consts;

import vip.e7du.dc.api.idc.IDC;
import vip.e7du.dc.api.idc.kits.SqlKit;
import vip.e7du.dc.api.idc.kits.SqlKit.Sql;

/**
 * @author Jobsz
 *
 */
public class RemoteDCProxy implements IDC {

    private void printSql(int idx, String type, Sql sql) {
        System.out.println("️sql index " + idx + " Type 😋" + type + "😋 ➡️ ➡️ ➡️ ➡️ " + sql + "⬅️ ⬅️ ⬅️ ️");
    }

    @Override
    public boolean delete(String subSysName, Object... datas) {
        System.out.println("invoke delete SubSysName \"" + subSysName + "\"");
        for (int i = 0; i < datas.length; i++) {
            this.printSql(i, "delete", SqlKit.delete(datas[i]));
        }
        return false;
    }

    @Override
    public boolean update(String subSysName, Object... datas) {
        System.out.println("invoke update SubSysName \"" + subSysName + "\"");
        for (int i = 0; i < datas.length; i++) {
            this.printSql(i, "update", SqlKit.update(datas[i]));
        }
        return false;
    }

    @Override
    public boolean save(String subSysName, Object... datas) {
        System.out.println("invoke save SubSysName \"" + subSysName + "\"");
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < datas.length; i++) {
            this.printSql(i, "save", SqlKit.save(datas[i]));
            list.add(i + 100);
        }
        return true;
    }

    @Override
    public Map<String, Object> query(String subSysName, int page, int size, Object... datas) {
        System.out.println("invoke query SubSysName \"" + subSysName + "\"");

        for (int i = datas.length - 1; i >= 0; i--) {
            this.printSql(i, "query", SqlKit.query(datas[i]));
        }

        List<Map<String, Object>> retData = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 10; i++) {
            Map<String, Object> testObj = new HashMap<String, Object>();
            testObj.put("name", "Jobsz" + i);
            testObj.put("addr" + i, "BeiJing" + i);
            retData.add(testObj);
        }

        Map<String, Object> data = new HashMap<String, Object>();
        data.put(Consts.QUERY_DATA, retData);
        data.put(Consts.QUERY_DATA_COUNT, 120);
        return data;
    }

}
