package org.muchu.mybatis.support.constant;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public enum MyBatisSQLTag {

    SELECT("select", "\nselect * from\n", MySQLAttrTag.ID),
    UPDATE("update", "\nupdate\n", MySQLAttrTag.ID),
    DELETE("delete", "\ndelete from\n", MySQLAttrTag.ID),
    INSERT("insert", "\ninsert into\n", MySQLAttrTag.ID);

    private String value;

    private List<MySQLAttrTag> attrTagList;

    private String bodyText;

    MyBatisSQLTag(String value, String bodyText, MySQLAttrTag... attrTagList) {
        this.value = value;
        this.bodyText = bodyText;
        this.attrTagList = Arrays.asList(attrTagList);
    }

    public String getValue() {
        return value;
    }

    public List<MySQLAttrTag> getAttrTagList() {
        return attrTagList;
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
