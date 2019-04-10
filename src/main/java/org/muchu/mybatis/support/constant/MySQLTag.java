package org.muchu.mybatis.support.constant;

import java.util.Arrays;
import java.util.List;

public enum MySQLTag {

    SELECT("select", MySQLAttrTag.ID),
    UPDATE("update", MySQLAttrTag.ID),
    DELETE("delete", MySQLAttrTag.ID),
    INSERT("insert", MySQLAttrTag.ID);

    private String value;

    private List<MySQLAttrTag> attrTagList;

    MySQLTag(String value, MySQLAttrTag... attrTagList) {
        this.value = value;
        this.attrTagList = Arrays.asList(attrTagList);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<MySQLAttrTag> getAttrTagList() {
        return attrTagList;
    }

    public void setAttrTagList(List<MySQLAttrTag> attrTagList) {
        this.attrTagList = attrTagList;
    }
}
