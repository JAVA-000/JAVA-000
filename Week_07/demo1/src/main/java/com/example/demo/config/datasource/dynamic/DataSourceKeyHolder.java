package com.example.demo.config.datasource.dynamic;

public class DataSourceKeyHolder {

    private final static ThreadLocal<String> dataSourceKey = new ThreadLocal<>();

    public static void set(String key) {
        dataSourceKey.set(key);
    }

    public static String get() {
        return dataSourceKey.get();
    }

    public static void clear() {
        dataSourceKey.remove();
    }

}
