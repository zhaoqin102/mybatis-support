package org.muchu.mybatis.support.constant;

import java.util.Objects;

public enum MyBatisSQLTag {

    SELECT("select", "\nselect * from\n"),
    UPDATE("update", "\nupdate\n"),
    DELETE("delete", "\ndelete from\n"),
    INSERT("insert", "\ninsert into\n");

    private final String value;

    private final String bodyText;

    MyBatisSQLTag(String value, String bodyText) {
        this.value = value;
        this.bodyText = bodyText;
    }

    public String getValue() {
        return value;
    }

    public String getBodyText() {
        return bodyText;
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
        return Objects.equals(DELETE.getValue(), value);
    }

}
