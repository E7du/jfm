package com.jfinal.plugin.activerecord;

/**
 * @author Jobsz
 */
public abstract class Consts {
    public static final String SYS_KEY_PREFIX    = "_sys";
    public static final String TABLE_KEY         = "_sys_t__";
    public static final String ATTR_KEY          = "_sys_dc_attr__";
    public static final String OUTPUT_KEY        = "_sys_dc_output__";
    public static final String PARAMS_KEY        = "_sys_dc_attr_params__";
    public static final String OPS_KEY           = "_sys_dc_attr_ops__";
    public static final String OUTPUT_COLUMN_KEY = "_sys_dc_output_col__";
    public static final String AS_KEY            = "_sys_dc_as__";
    public static final String ORDER_KEY         = "_sys_order__";

    public static final String DEFAULT_OUTPUT    = "id";
    public static final int    DEFAULT_PAGE      = 1;
    public static final int    DEFALUT_SIZE      = 30;
    public static final String QUERY_DATA_COUNT  = "__data_cnt__";
    public static final String QUERY_DATA        = "__data__";
    public static final String ORDER_ASC         = " ASC ";
    public static final String ORDER_DESC        = " DESC ";
}
