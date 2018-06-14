package com.jfinal.plugin.activerecord;

import java.util.UUID;

/**
 * @author Jobsz
 */
public class DefaultValueKits {

    private DefaultValueKits() {
    }

    @SuppressWarnings("unchecked")
    //TODO unsupported Boolean 
    public static <T> T getDefaultValue(T t) {
        if (t instanceof Boolean) {
            throw new IllegalArgumentException("DefaultValueKits unsupported Boolean ");
        }
        T _t = (T) new Object();
        int hashCode = UUID.randomUUID().hashCode();
        if (t instanceof String) {
            _t = (T) new String(String.valueOf(hashCode));
        } else if (t instanceof java.math.BigInteger) {
            _t = (T) new java.math.BigInteger(String.valueOf(hashCode));
        } else if (t instanceof java.util.Date) {
            _t = (T) new java.util.Date(hashCode);
        } else if (t instanceof byte[]) {
            _t = (T) new byte[] { (byte) hashCode };
        } else if (t instanceof Integer) {
            _t = (T) new Integer(hashCode);
        } else if (t instanceof Long) {
            _t = (T) new Long(hashCode);
        } else if (t instanceof Float) {
            _t = (T) new Float(hashCode);
        } else if (t instanceof Double) {
            _t = (T) new Double(hashCode);
        } else if (t instanceof java.math.BigDecimal) {
            _t = (T) new java.math.BigDecimal(hashCode);
        } else if (t instanceof Number) {
            _t = (T) new Integer(hashCode);
        }
        return _t;
    }
}
