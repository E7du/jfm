package vip.e7du.dc.api.dc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.jfinal.plugin.activerecord.Consts;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Model.QueryOut;

import vip.e7du.dc.api.idc.IDC;

/**
 * @author Jobsz
 */
public abstract class DC {

    @Autowired
    protected IDC idc;

    protected abstract String subSysName();

    private Object[] toArray(Model<?>... datas) {
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < datas.length; i++) {
            list.add(datas[i].dcExport().get(Consts.ATTR_KEY));
        }
        return list.toArray();
    }

    /**
     * Delete Data
     * 
     * @param datas
     * @return true:success;false:failure
     */
    public boolean delete(Model<?>... datas) {
        Object[] _datas = this.toArray(datas);
        return this.idc.delete(this.subSysName(), _datas);
    }

    /**
     * Update Data, the data must contain PrimaryKey column value
     * 
     * @param datas
     * @return true:success;false:failure
     */
    public boolean update(Model<?>... datas) {
        Object[] _datas = this.toArray(datas);
        return this.idc.update(this.subSysName(), _datas);
    }

    /**
     * Save Data
     * 
     * @param datas
     * @return save data success return ids, otherwise return empty list.
     */
    public boolean save(Model<?>... datas) {
        Object[] _datas = this.toArray(datas);
        return this.idc.save(this.subSysName(), _datas);
    }

    /**
     * Query data, Only Support query data from two databases tables , and first
     * query the last table, and filter in first table data return this data.
     * 
     * @param datas
     * @return query output that contains data and data count
     */
    public QueryOut query(Model<?>... datas) {
        return this.query(-1, -1, datas);
    }

    /**
     * Query data, Only Support query data from two databases tables , and first
     * query the last table, and filter in first table data return this data.
     * 
     * @param datas
     * @return Model<?>
     * @author sujiuxiang
     */
    @SuppressWarnings("unchecked")
    public <T extends Model<T>> T queryOne(Model<?>... datas) {
        QueryOut queryOut = this.query(-1, 1, datas);
        List<T> list = queryOut.getQueryData();
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        //use first class return data
        Class<? extends Model<?>> modelClass = (Class<? extends Model<?>>) datas[0].getClass();
        T instance = null;
        try {
            instance = (T) modelClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
        }
        return instance;
    }

    /**
     * Query data, Only Support query data from two databases tables , and first
     * query the last table, and filter in first table data return this data.
     * 
     * @param page
     *            : data page , the first page is zero
     * @param size
     *            : data size
     * @param datas
     * @return query output that contains data and data count
     */
    @SuppressWarnings("unchecked")
    public <T extends Model<T>> QueryOut query(int page, int size, Model<?>... datas) {
        Object[] _datas = this.toArray(datas);

        Map<String, Object> idcRet = this.idc.query(this.subSysName(), page, size, _datas);
        List<Map<String, Object>> retData = (List<Map<String, Object>>) idcRet.get(Consts.QUERY_DATA);

        List<T> queryRetData = new ArrayList<T>();
        //use first class return data
        Class<? extends Model<?>> modelClass = (Class<? extends Model<?>>) datas[0].getClass();
        for (int index = 0; index < retData.size(); index++) {
            T instance = null;
            try {
                instance = (T) modelClass.newInstance();
                instance.put(retData.get(index));
                Object id = instance.get(Consts.DEFAULT_OUTPUT);
                if (id != null) {
                    instance.set(Consts.DEFAULT_OUTPUT, id);
                }
            } catch (InstantiationException | IllegalAccessException e) {
                throw (new RuntimeException(e.getLocalizedMessage()));
            }
            queryRetData.add(instance);
        }

        //cover data
        idcRet.put(Consts.QUERY_DATA, queryRetData);
        return (new QueryOut(idcRet));
    }
}
