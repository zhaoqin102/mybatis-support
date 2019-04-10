package org.muchu.mybatis.support.constant;

public enum MySQLAttrTag {
    ID("id");

    private String value;

    MySQLAttrTag(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
