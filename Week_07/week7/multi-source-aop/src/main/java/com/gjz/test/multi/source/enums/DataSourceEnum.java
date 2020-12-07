package com.gjz.test.multi.source.enums;

/**
 *
 * <pre>
 *
 * </pre>
 * @author guojz
 * @version 1.00.00
 * @createDate 2020/12/7
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public enum DataSourceEnum{
    MASTER("主数据库", "master"),
    SALVE("从数据库", "salve")
    ;

    private String name;
    private String value;

    DataSourceEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}