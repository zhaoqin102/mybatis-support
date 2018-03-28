package org.muchu.mybatis.support.constant;

import java.util.Objects;

/**
 * @author heber
 */
public enum MyBatisTag {

    MAPPER("mapper"),
    SELECT("select"),
    UPDATE("update"),
    DELETE("delete"),
    INSERT("insert"),
    ID("id");

    private String value;

    MyBatisTag(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static boolean isCRUDStatement(String value) {
        if (Objects.equals(INSERT.getValue(), value)) {
            return true;
        }
        if (Objects.equals(SELECT.getValue(), value)) {
            return true;
        }
        if (Objects.equals(UPDATE.getValue(), value)) {
            return true;
        }
        if (Objects.equals(DELETE.getValue(), value)) {
            return true;
        }
        return false;
    }
}
