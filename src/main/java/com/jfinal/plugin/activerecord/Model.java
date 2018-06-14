/**
 * Copyright (c) 2011-2016, James Zhan 詹波 (jfinal@126.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jfinal.plugin.activerecord;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

//import com..gp.storage.dc.api.dc.DC;

/**
 * Model.
 * <p>
 * A clever person solves a problem. A wise person avoids it. A stupid person
 * makes it.
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class Model<M extends Model> implements Serializable {

    private static final long   serialVersionUID = -990334519496260591L;

    /**
     * Attributes of this model
     */
    private Map<String, Object> attrs            = new HashMap<String, Object>();

    /**
     * Set attribute to model.
     * 
     * @param attr
     *            the attribute name of the model
     * @param value
     *            the value of the attribute
     * @return this model
     */
    public M set(String attr, Object value) {
        return this.setAttr(attr, value);
    }

    /**
     * Put key value pair to the model without check attribute name.
     */
    public M put(String key, Object value) {
        return this.set(key, value);
    }

    /**
     * Put map to the model without check attribute name.
     */
    public M put(Map<String, Object> map) {
        attrs.putAll(map);
        return (M) this;
    }

    /**
     * Put other model to the model without check attribute name.
     */
    public M put(Model model) {
        attrs.putAll(model.getAttrs());
        return (M) this;
    }

    /**
     * Get attribute of any mysql type
     */
    public <T> T get(String attr) {
        return (T) attrs.get(attr);
    }

    /**
     * Get attribute of any mysql type. Returns defaultValue if null.
     */
    public <T> T get(String attr, Object defaultValue) {
        Object result = attrs.get(attr);
        return (T) (result != null ? result : defaultValue);
    }

    /**
     * Get attribute of mysql type: varchar, char, enum, set, text, tinytext,
     * mediumtext, longtext
     */
    public String getStr(String attr) {
        return (String) this.get(attr, "");//(String) attrs.get(attr);
    }

    /**
     * Get attribute of mysql type: int, integer, tinyint(n) n > 1, smallint,
     * mediumint
     */
    public Integer getInt(String attr) {
        return (Integer) this.get(attr, 0);//(Integer) attrs.get(attr);
    }

    /**
     * Get attribute of mysql type: bigint, unsign int
     */
    public Long getLong(String attr) {
        return (Long) this.get(attr, 0L);//(Long) attrs.get(attr);
    }

    /**
     * Get attribute of mysql type: unsigned bigint
     */
    public java.math.BigInteger getBigInteger(String attr) {
        return (java.math.BigInteger) this.get(attr, new java.math.BigInteger("0"));//(java.math.BigInteger) attrs.get(attr);
    }

    /**
     * Get attribute of mysql type: date, year
     */
    public java.util.Date getDate(String attr) {
        return (java.util.Date) this.get(attr, new java.util.Date());//(java.util.Date) attrs.get(attr);
    }

    /**
     * Get attribute of mysql type: time
     */
    public java.sql.Time getTime(String attr) {
        return (java.sql.Time) this.get(attr, new java.util.Date());//(java.sql.Time) attrs.get(attr);
    }

    /**
     * Get attribute of mysql type: timestamp, datetime
     */
    public java.sql.Timestamp getTimestamp(String attr) {
        return (java.sql.Timestamp) this.get(attr, new java.util.Date());//(java.sql.Timestamp) attrs.get(attr);
    }

    /**
     * Get attribute of mysql type: real, double
     */
    public Double getDouble(String attr) {
        return (Double) this.get(attr, 0.0);//(Double) attrs.get(attr);
    }

    /**
     * Get attribute of mysql type: float
     */
    public Float getFloat(String attr) {
        return (Float) this.get(attr, 0.0);//(Float) attrs.get(attr);
    }

    /**
     * Get attribute of mysql type: bit, tinyint(1)
     */
    public Boolean getBoolean(String attr) {
        return (Boolean) this.get(attr, Boolean.FALSE);// (Boolean) attrs.get(attr);
    }

    /**
     * Get attribute of mysql type: decimal, numeric
     */
    public java.math.BigDecimal getBigDecimal(String attr) {
        return (java.math.BigDecimal) this.get(attr, new java.math.BigDecimal(0));//(java.math.BigDecimal) attrs.get(attr);
    }

    /**
     * Get attribute of mysql type: binary, varbinary, tinyblob, blob,
     * mediumblob, longblob
     */
    public byte[] getBytes(String attr) {
        return this.get(attr, new byte[0]);//(byte[]) attrs.get(attr);
    }

    /**
     * Get attribute of any type that extends from Number
     */
    public Number getNumber(String attr) {
        return (Number) this.get(attr, 0); //(Number) attrs.get(attr);
    }

    /**
     * Return attribute Map.
     * <p>
     * Danger! The update method will ignore the attribute if you change it
     * directly. You must use set method to change attribute that update method
     * can handle it.
     */
    protected Map<String, Object> getAttrs() {
        return attrs;
    }

    /**
     * Return attribute Set.
     */
    public Set<Entry<String, Object>> getAttrsEntrySet() {
        return attrs.entrySet();
    }

    /**
     * Remove attribute of this model.
     * 
     * @param attr
     *            the attribute name of the model
     * @return this model
     */
    public M remove(String attr) {
        attrs.remove(attr);
        return (M) this;
    }

    /**
     * Remove attributes of this model.
     * 
     * @param attrs
     *            the attribute names of the model
     * @return this model
     */
    public M remove(String... attrs) {
        if (attrs != null)
            for (String a : attrs) {
                this.attrs.remove(a);
            }
        return (M) this;
    }

    /**
     * Remove attributes if it is null.
     * 
     * @return this model
     */
    public M removeNullValueAttrs() {
        for (Iterator<Entry<String, Object>> it = attrs.entrySet().iterator(); it.hasNext();) {
            Entry<String, Object> e = it.next();
            if (e.getValue() == null) {
                it.remove();
            }
        }
        return (M) this;
    }

    /**
     * Keep attribute of this model and remove other attributes.
     * 
     * @param attr
     *            the attribute name of the model
     * @return this model
     */
    public M keep(String attr) {
        if (attrs.containsKey(attr)) { // prevent put null value to the newColumns
            Object keepIt = attrs.get(attr);
            attrs.clear();
            attrs.put(attr, keepIt);
        } else {
            attrs.clear();
        }
        return (M) this;
    }

    /**
     * Remove all attributes of this model.
     * 
     * @return this model
     */
    public M clear() {
        attrs.clear();
        return (M) this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append(" {");
        boolean first = true;
        for (Entry<String, Object> e : attrs.entrySet()) {
            if (first)
                first = false;
            else
                sb.append(", ");

            Object value = e.getValue();
            if (value != null)
                value = value.toString();
            sb.append(e.getKey()).append(":").append(value);
        }
        sb.append("}");
        return sb.toString();
    }

    public boolean equals(Object o) {
        if (!(o instanceof Model))
            return false;
        if (getUsefulClass() != ((Model) o).getUsefulClass())
            return false;
        if (o == this)
            return true;
        return this.attrs.equals(((Model) o).attrs);
    }

    public int hashCode() {
        return (attrs == null ? 0 : attrs.hashCode());
    }

    /**
     * Return attribute names of this model.
     */
    public String[] getAttrNames() {
        Set<String> attrNameSet = attrs.keySet();
        return attrNameSet.toArray(new String[attrNameSet.size()]);
    }

    /**
     * Return attribute values of this model.
     */
    public Object[] getAttrValues() {
        java.util.Collection<Object> attrValueCollection = attrs.values();
        return attrValueCollection.toArray(new Object[attrValueCollection.size()]);
    }

    private Class<? extends Model> getUsefulClass() {
        Class c = getClass();
        return c.getName().indexOf("EnhancerByCGLIB") == -1 ? c : c.getSuperclass(); // com.demo.blog.Blog$$EnhancerByCGLIB$$69a17158
    }

    //===========================E7du=========

    private static final String   LIKE           = " LIKE ";
    private static final String   EQ             = " = ";
    private static final String   NEQ            = " != ";
    private static final String   BETWEEN        = " BETWEEN ";
    private static final String   AND            = " AND ";
    private static final String   OR             = " OR ";
    private static final String   GE             = " >= ";
    private static final String   GT             = " > ";
    private static final String   LE             = " <= ";
    private static final String   LT             = " < ";
    private static final String   IN             = " IN ";

    protected static final String TABLE_KEY      = Consts.TABLE_KEY;

    /**
     * Sql Attr of this model
     */
    private Map<String, Object>   sqlAttrs       = new HashMap<String, Object>();

    /**
     * Sql Params Attr of this model
     */
    private Map<String, Object>   sqlParamsAttrs = new HashMap<String, Object>();

    /**
     * Sql Ops Attr of this model
     */
    private Map<String, Object>   sqlOpsAttrs    = new HashMap<String, Object>();

    protected Model() {
        this.setOutput(Consts.DEFAULT_OUTPUT);
    }

    private boolean isSysKey(String key) {
        return StringUtils.startsWith(key, Consts.SYS_KEY_PREFIX);
    }

    private M clone(Map<String, Object> attr) {
        M m = this.newInstance();
        m.put(attr);
        return m;
    }

    /**
     * Set attribute to model.
     * 
     * @param attr
     *            the attribute name of the model
     * @param value
     *            the value of the attribute
     * @return this model
     */
    private M setAttr(String attr, Object value) {
        //当 attr 为 __t__ 时直接放入 value
        if (this.isSysKey(attr)) {
            this.attrs.put(attr, value);
            this.sqlAttrs.put(attr, value);
            return (M) this;
        }

        Object attrVal = value;
        Match match = null;
        //query logic
        if ((match = Match.matches.get(value)) != null) {
            attrVal = match.vals[0];
            if (match.isAndOr()) {
                this.sqlOpsAttrs.put(attr, match.ops);
                match.ops = Model.EQ;
            }
            //remove mq
            Match.matches.remove(value);
        } else {
            match = new Match(value, Model.EQ, value);
        }
        //params
        this.sqlParamsAttrs.put(attr, match.vals);
        this.sqlAttrs.put(attr, match.getAttrSqlValue());
        this.attrs.put(attr, attrVal);
        return (M) this;
    }

    /**
     * Create new Instance
     */
    public M newInstance() {
        M m = null;
        try {
            m = (M) this.getClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw (new RuntimeException(e.getLocalizedMessage()));
        }
        return m;
    }

    /**
     * DC Data prepare
     */
    public Map<String, Object> dcExport() {
        this.sqlAttrs.put(Consts.OPS_KEY, this.sqlOpsAttrs);
        this.sqlAttrs.put(Consts.PARAMS_KEY, this.sqlParamsAttrs);
        this.attrs.put(Consts.ATTR_KEY, this.sqlAttrs);
        return this.removeNullValueAttrs().getAttrs();
    }

    /**
     * Clone Model
     */
    public M clone() {
        return (M) this.clone(this.efficientAttrs().getAttrs());
    }

    /**
     * get efficient Attrs
     */
    public M efficientAttrs() {
        for (Iterator<Entry<String, Object>> it = attrs.entrySet().iterator(); it.hasNext();) {
            Entry<String, Object> e = it.next();
            if (this.isSysKey(e.getKey()) || e.getValue() == null) {
                it.remove();
            }
        }
        return (M) this;
    }

    /**
     * Set Orders
     * 
     * @param orders
     */
    public M setOrders(String... orders) {
        this.attrs.put(Consts.ORDER_KEY, orders);
        this.sqlAttrs.put(Consts.ORDER_KEY, orders);
        return (M) this;
    }

    /**
     * clear soma fields
     * 
     * @param fields
     *            that will be remove from cloned object
     * @return cloned object
     */
    public M clear(Object... fields) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.putAll(this.attrs);

        for (int i = 0; i < fields.length; i++) {
            Object key = fields[i];
            if (map.containsKey(key)) {
                map.remove(key);
            }
        }
        return (M) this.clone(map).efficientAttrs();
    }

    /**
     * Set Select output content
     * 
     * @param output
     * @return
     */
    public M setOutput(String output) {
        return this.setOutput(output, null);
    }

    /**
     * Set Select output content
     * 
     * @param output
     * @return
     */
    public M setOutput(String output, String as) {
        if (null == output) {
            return (M) this;
        }

        if (null == as) {
            as = output;
        }

        if ("*".equals(output)) {
            this.set(Consts.OUTPUT_KEY, output);
            return (M) this;
        }

        String column = output;
        String alias = as;

        StringBuilder sbr = new StringBuilder();

        if (output.equals(as)) {
            alias = column;
            sbr.append("`").append(column).append("`");
        } else {
            sbr.append("`").append(column).append("`").append(" AS ").append(alias);
        }

        this.set(Consts.AS_KEY, alias);
        this.set(Consts.OUTPUT_COLUMN_KEY, column);
        this.set(Consts.OUTPUT_KEY, sbr.toString());
        return (M) this;
    }

    //===============Value Match=============

    public static class Match {

        private Object                    mq      = null;
        private String                    ops     = "";
        private Object[]                  vals    = null;

        private static Map<Object, Match> matches = new HashMap<Object, Match>();

        private Match(Object mq, String ops, Object... vals) {
            this.mq = mq;
            this.ops = ops;
            this.vals = vals;
        }

        private String getAttrSqlValue() {
            StringBuilder sbr = new StringBuilder();
            sbr.append(this.ops);
            if (Model.IN.equals(this.ops)) {
                sbr.append("(");
                for (int i = 0; i < vals.length; i++) {
                    sbr.append("?,");
                }
                sbr.deleteCharAt(sbr.length() - 1).append(") ");
                return sbr.toString();
            }
            sbr.append("?");
            if (Model.BETWEEN.equals(this.ops)) {
                sbr.append(Model.AND).append("? ");
            }
            return sbr.toString();
        }

        private static <T> T getMq(T t) {
            return DefaultValueKits.getDefaultValue(t);
        }

        private boolean isAndOr() {
            return Model.AND.equals(this.ops) || Model.OR.equals(this.ops);
        }

        @Override
        public String toString() {
            StringBuilder sbr = new StringBuilder();
            sbr.append("Match[" + this.hashCode() + "] [mq=\"" + mq.getClass() + "\",ops=\"" + ops + "\", vals=\"" + Arrays.toString(vals) + "\"]\n");
            sbr.append("vals Detail:\n");
            for (int i = 0; i < vals.length; i++) {
                sbr.append("index").append(i).append("value=[").append(vals[i]).append("],Class=[").append(vals[i].getClass()).append("]\n");
            }
            return sbr.toString();
        }

        /**
         * column = 'value'
         */
        public static <T> T EQ(T value) {
            T mq = Match.getMq(value);
            Match.matches.put(mq, new Match(mq, Model.EQ, value));
            return mq;
        }

        /**
         * column != 'value'
         */
        public static <T> T NEQ(T value) {
            T mq = Match.getMq(value);
            Match.matches.put(mq, new Match(mq, Model.NEQ, value));
            return mq;
        }

        /**
         * column LIKE 'value'
         */
        public static <T> T LIKE(T value) {
            T mq = Match.getMq(value);
            Match.matches.put(mq, new Match(mq, Model.LIKE, value));
            return mq;
        }

        /**
         * column >= 'value'
         */
        public static <T> T GE(T value) {
            T mq = Match.getMq(value);
            Match.matches.put(mq, new Match(mq, Model.GE, value));
            return mq;
        }

        /**
         * column > 'value'
         */
        public static <T> T GT(T value) {
            T mq = Match.getMq(value);
            Match.matches.put(mq, new Match(mq, Model.GT, value));
            return mq;
        }

        /**
         * column <= 'value'
         */
        public static <T> T LE(T value) {
            T mq = Match.getMq(value);
            Match.matches.put(mq, new Match(mq, Model.LE, value));
            return mq;
        }

/**
    	 *  column < 'value'
    	 */
        public static <T> T LT(T value) {
            T mq = Match.getMq(value);
            Match.matches.put(mq, new Match(mq, Model.LT, value));
            return mq;
        }

        /**
         * column1 AND column2
         */
        public static <T> T AND(T value) {
            T mq = Match.getMq(value);
            Match.matches.put(mq, new Match(mq, Model.AND, value));
            return mq;
        }

        /**
         * column1 OR column2
         */
        public static <T> T OR(T value) {
            T mq = Match.getMq(value);
            Match.matches.put(mq, new Match(mq, Model.OR, value));
            return mq;
        }

        /**
         * BETWEEN 'start' AND 'end'
         */
        public static <T> T BW(T start, T end) {
            T mq = Match.getMq(start);
            Match.matches.put(mq, new Match(mq, Model.BETWEEN, start, end));
            return mq;
        }

        /**
         * column > 'value'
         */
        public static <T> T IN(T... values) {
            T mq = Match.getMq(values[0]);
            if (values.length == 1) {
                Match.matches.put(mq, new Match(mq, Model.EQ, values[0]));
            } else {
                Match.matches.put(mq, new Match(mq, Model.IN, values));
            }
            return mq;
        }
    }

    //===============db=============

    public static class QueryOut implements Serializable {

        private static final long   serialVersionUID = 8208414208144431180L;

        private Map<String, Object> attrs            = new HashMap<String, Object>();

        public QueryOut(Map<String, Object> map) {
            this.attrs.putAll(map);
        }

        public <T extends Model<T>> List<T> getQueryData() {
            return (List<T>) this.attrs.get(Consts.QUERY_DATA);
        }

        public Long getQueryCount() {
            return Long.parseLong(this.attrs.get(Consts.QUERY_DATA_COUNT).toString());
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("\nQueryDataCount =>").append(this.getQueryCount());
            sb.append("\nQueryOutData =>").append("\n[\n");
            List<Object> data = (List<Object>) this.attrs.get(Consts.QUERY_DATA);
            for (Object object : data) {
                sb.append(object).append(", \n");
            }
            sb.delete(sb.length() - ", \n".length(), sb.length());
            sb.append("\n]");
            return sb.toString();
        }
    }

    //    private DC dc = null;
    //
    //    private void checkDc() {
    //        if (null == this.dc) {
    //            throw new IllegalArgumentException("dc is nullpoint. please `selectDCNode` first.");
    //        }
    //    }
    //
    //    /**
    //     * Select DC
    //     */
    //    public M selectDC(DC dc) {
    //        this.dc = dc;
    //        return (M) this;
    //    }
    //
    //    /**
    //     * Query data
    //     * 
    //     * @return list data
    //     */
    //    public QueryOut query() {
    //        return this.query(Consts.DEFAULT_PAGE, Consts.DEFALUT_SIZE);
    //    }
    //
    //    /**
    //     * Query data
    //     * 
    //     * @return list data
    //     */
    //    public QueryOut query(int page, int size) {
    //        this.checkDc();
    //        return this.dc.query(page, size, this);
    //    }
    //
    //    /**
    //     * Query data
    //     * 
    //     * @return list data
    //     */
    //    public QueryOut find() {
    //        return this.find(Consts.DEFAULT_PAGE, Consts.DEFALUT_SIZE);
    //    }
    //
    //    /**
    //     * Query data
    //     * 
    //     * @return list data
    //     */
    //    public QueryOut find(int page, int size) {
    //        this.checkDc();
    //        return this.dc.query(page, size);
    //    }
    //
    //    /**
    //     * save data
    //     * 
    //     * @return success return id, otherwise return `null`.
    //     */
    //    public void save() {
    //        this.checkDc();
    //        this.dc.save(this);
    //    }
    //
    //    /**
    //     * update data
    //     * 
    //     * @return success:true,failure:false.
    //     */
    //    public boolean update() {
    //        this.checkDc();
    //        return this.dc.update(this);
    //    }
    //
    //    /**
    //     * delete data
    //     * 
    //     * @return success:true,failure:false.
    //     */
    //    public boolean delete() {
    //        this.checkDc();
    //        return this.dc.delete(this);
    //    }
}