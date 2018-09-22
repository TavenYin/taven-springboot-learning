package com.gitee.taven.config;

public class DynamicDataSourceContextHolder {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>() {
        @Override
        protected String initialValue() {
            return "dynamic_db0";
        }
    };

    /**
     * To switch DataSource
     *
     * @param key the key
     */
    public static void setDataSourceKey(String key) {
        contextHolder.set(key);
    }

    /**
     * Get current DataSource
     *
     * @return data source key
     */
    public static String getDataSourceKey() {
        return contextHolder.get();
    }

    /**
     * To set DataSource as default
     */
    public static void clearDataSourceKey() {
        contextHolder.remove();
    }

}
