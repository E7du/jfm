package vip.e7du.dc.api.idc.kits;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.jfinal.plugin.activerecord.Consts;

/**
 * SqlKit
 * 
 * @author Jobsz
 */
public class SqlKit {

    public static Pattern replaceCntPattern = Pattern.compile("SELECT.+FROM");

    public static class Sql {

        private StringBuilder sql    = new StringBuilder("");
        private List<Object>  params = new ArrayList<Object>();

        public Sql() {
        }

        public Sql(String sql, Object[] params) {
            this.sql.append(sql);
            this.params = new ArrayList<Object>(Arrays.asList(params));
        }

        public String sql() {
            return this.sql.toString();
        }

        public Object[] params() {
            return this.params.toArray();
        }

        public Sql page(int page, int size) {
            this.sql.append(" LIMIT ?, ?");
            List<Object> paramlst = new ArrayList<Object>();
            Collections.addAll(paramlst, this.params.toArray());
            paramlst.add((page - 1) * size);
            paramlst.add(size);
            this.params = paramlst;
            return this;
        }

        public String cnt() {
            return replaceCntPattern.matcher(this.sql()).replaceFirst("SELECT count(1) as cnt FROM");
        }

        public Sql orderBy(String... orders) {
            this.sql.append(" ORDER BY ");
            String suffix = ", ";
            for (String column : orders) {
                this.sql.append(column).append(suffix);
            }
            this.sql.delete(this.sql.length() - suffix.length(), this.sql.length());
            return this;
        }

        public Sql in(String field, Object... values) {
            this.sql.append(this.sql.indexOf("WHERE") == -1 ? " WHERE " : " AND ");
            this.sql.append(field);

            if (values.length == 1) {
                this.sql.append(" = ? ");
                params.add(values[0]);
                return this;
            }

            this.sql.append(" IN (");
            for (Object val : values) {
                this.sql.append("?,");
                params.add(val);
            }
            this.sql.deleteCharAt(this.sql.length() - 1).append(") ");
            return this;
        }

        @Override
        public String toString() {
            return "{ " + sql + " } params=" + params;
        }
    }

    private static void removeFields(Map<?, ?> map, Object... fields) {
        if (null == fields) {
            return;
        }

        for (int i = 0; i < fields.length; i++) {
            Object key = fields[i];
            if (map.containsKey(key)) {
                map.remove(key);
            }
        }
    }

    private static void fillValues(List<Object> vals, Object params) {
        if (!params.getClass().isArray()) {
            vals.add(params);
            return;
        }

        Object[] objs = (Object[]) params;
        for (int i = 0; i < objs.length; i++) {
            vals.add(objs[i]);
        }
    }

    @SuppressWarnings("unchecked")
    private static Map<?, ?> cloneMap(Object map) {
        Map<String, Object> clonedMap = new HashMap<String, Object>();
        clonedMap.putAll((Map<String, Object>) map);
        return clonedMap;
    }

    /**
     * Make Delete data Sql
     * 
     * @param data
     * @return delete sql
     */
    @SuppressWarnings("unchecked")
    public static Sql delete(Object data) {
        if (!(data instanceof Map)) {
            return (new Sql());
        }

        Map<?, ?> map = SqlKit.cloneMap(data);
        Map<String, Object> params = (Map<String, Object>) map.get(Consts.PARAMS_KEY);
        if (null == params || params.size() == 0) {
            return (new Sql());
        }

        SqlKit.removeFields(map, Consts.OUTPUT_KEY, Consts.AS_KEY, Consts.OUTPUT_COLUMN_KEY, Consts.ORDER_KEY);

        StringBuilder sbr = new StringBuilder();
        sbr.append("DELETE FROM `").append(map.get(Consts.TABLE_KEY)).append("` WHERE ");
        SqlKit.removeFields(map, Consts.TABLE_KEY, Consts.PARAMS_KEY);

        Map<String, Object> ops = (Map<String, Object>) map.get(Consts.OPS_KEY);
        SqlKit.removeFields(map, Consts.OPS_KEY);

        List<Object> vals = new ArrayList<Object>();
        String suffix = " AND ";

        for (Object key : map.keySet()) {
            if (!ops.containsKey(key)) {
                sbr.append("`").append(key).append("`").append(map.get(key)).append(suffix);
                SqlKit.fillValues(vals, params.get(key));
            }
        }
        sbr.delete(sbr.length() - suffix.length(), sbr.length());

        for (Object opsKey : ops.keySet()) {
            suffix = ops.get(opsKey).toString();
            sbr.append(suffix).append("`").append(opsKey).append("` ").append(map.get(opsKey));
            SqlKit.fillValues(vals, params.get(opsKey));
        }

        return (new Sql(sbr.toString(), vals.toArray()));
    }

    /**
     * Make Update data Sql
     * 
     * @param data
     * @return update sql
     */
    @SuppressWarnings("unchecked")
    public static Sql update(Object data) {
        if (!(data instanceof Map)) {
            return (new Sql());
        }

        Map<?, ?> map = SqlKit.cloneMap(data);
        Map<String, Object> params = (Map<String, Object>) map.get(Consts.PARAMS_KEY);
        if (null == params || params.size() == 0) {
            return (new Sql());
        }

        SqlKit.removeFields(map, Consts.OUTPUT_KEY, Consts.AS_KEY, Consts.OUTPUT_COLUMN_KEY, Consts.ORDER_KEY);

        if (!map.containsKey(Consts.DEFAULT_OUTPUT)) {
            throw new IllegalArgumentException("Not Found PrimaryKey!");
        }
        StringBuilder sbr = new StringBuilder();
        sbr.append("UPDATE `").append(map.get(Consts.TABLE_KEY)).append("` SET ");
        map.remove(Consts.TABLE_KEY);
        Object id = map.get(Consts.DEFAULT_OUTPUT);
        map.remove(Consts.DEFAULT_OUTPUT);

        SqlKit.removeFields(map, Consts.PARAMS_KEY, Consts.OPS_KEY);

        List<Object> vals = new ArrayList<Object>();
        String suffix = ", ";

        for (Object key : map.keySet()) {
            sbr.append("`").append(key).append("`").append(map.get(key)).append(suffix);
            SqlKit.fillValues(vals, params.get(key));
        }
        sbr.delete(sbr.length() - suffix.length(), sbr.length());

        sbr.append(" WHERE `").append(Consts.DEFAULT_OUTPUT).append("`").append(id);
        SqlKit.fillValues(vals, params.get(Consts.DEFAULT_OUTPUT));
        return (new Sql(sbr.toString(), vals.toArray()));
    }

    /**
     * Make Save data Sql
     * 
     * @param data
     * @return insert sql
     */
    @SuppressWarnings("unchecked")
    public static Sql save(Object data) {
        if (!(data instanceof Map)) {
            return (new Sql());
        }

        Map<?, ?> map = SqlKit.cloneMap(data);
        Map<String, Object> params = (Map<String, Object>) map.get(Consts.PARAMS_KEY);
        if (null == params || params.size() == 0) {
            return (new Sql());
        }

        SqlKit.removeFields(map, Consts.OUTPUT_KEY, Consts.AS_KEY, Consts.OUTPUT_COLUMN_KEY, Consts.ORDER_KEY, Consts.PARAMS_KEY, Consts.OPS_KEY);

        StringBuilder sbr = new StringBuilder();
        sbr.append("INSERT INTO `").append(map.get(Consts.TABLE_KEY)).append("` ");
        map.remove(Consts.TABLE_KEY);

        StringBuilder columns = new StringBuilder("(");
        StringBuilder values = new StringBuilder("(");
        List<Object> vals = new ArrayList<Object>();
        String suffix = ", ";

        for (Object key : map.keySet()) {
            columns.append("`").append(key).append("`").append(suffix);
            values.append("?").append(suffix);
            SqlKit.fillValues(vals, params.get(key));
        }
        columns.delete(columns.length() - suffix.length(), columns.length());
        values.delete(values.length() - suffix.length(), values.length());

        columns.append(")");
        values.append(")");
        sbr.append(columns).append(" VALUES ").append(values);
        return (new Sql(sbr.toString(), vals.toArray()));
    }

    /**
     * Make Query data Sql
     * 
     * @param data
     * @return select sql
     */
    @SuppressWarnings("unchecked")
    public static Sql query(Object data) {
        if (!(data instanceof Map)) {
            return (new Sql());
        }

        Map<?, ?> map = SqlKit.cloneMap(data);
        Map<String, Object> params = (Map<String, Object>) map.get(Consts.PARAMS_KEY);

        String output = map.get(Consts.OUTPUT_KEY).toString();
        SqlKit.removeFields(map, Consts.OUTPUT_KEY, Consts.AS_KEY, Consts.OUTPUT_COLUMN_KEY, Consts.ORDER_KEY, Consts.PARAMS_KEY);

        Map<String, Object> ops = (Map<String, Object>) map.get(Consts.OPS_KEY);
        SqlKit.removeFields(map, Consts.OPS_KEY);

        StringBuilder sbr = new StringBuilder();
        sbr.append("SELECT ").append(output).append(" FROM `").append(map.get(Consts.TABLE_KEY)).append("` ");
        map.remove(Consts.TABLE_KEY);

        if (null == params || params.size() == 0) {
            return (new Sql(sbr.toString(), new Object[0]));
        }

        sbr.append("WHERE ");

        List<Object> vals = new ArrayList<Object>();
        String suffix = " AND ";

        for (Object key : map.keySet()) {
            if (!ops.containsKey(key)) {
                sbr.append("`").append(key).append("`").append(map.get(key)).append(suffix);
                SqlKit.fillValues(vals, params.get(key));
            }
        }
        sbr.delete(sbr.length() - suffix.length(), sbr.length());

        for (Object opsKey : ops.keySet()) {
            suffix = ops.get(opsKey).toString();
            sbr.append(suffix).append("`").append(opsKey).append("`").append(map.get(opsKey));
            SqlKit.fillValues(vals, params.get(opsKey));
        }

        return (new Sql(sbr.toString(), vals.toArray()));
    }

}
