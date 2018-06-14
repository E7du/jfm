package vip.e7du.dc.api.idc;

import java.util.Map;

/**
 * @author Jobsz
 */
//@Export
public interface IDC {

    /**
     * Delete data
     * 
     * @param subSysName
     * @param datas
     * @return true: success; false: failure;
     */
    public boolean delete(String subSysName, Object... datas);

    /**
     * Update data, the data must contain PrimaryKey column value
     * 
     * @param subSysName
     * @param datas
     * @return true:success;false:failure
     */
    public boolean update(String subSysName, Object... datas);

    /**
     * Save Data
     * 
     * @param subSysName
     * @param datas
     */
    public boolean save(String subSysName, Object... datas);

    /**
     * Query data,support query data from two database tables , and first query
     * the last table, and filter in first table data return this data.
     * 
     * @param subSysName
     * @param datas
     * @return query output, data into the map
     */
    public Map<String, Object> query(String subSysName, int page, int size, Object... datas);
}
