package org.muchu.mybatis.support.constant;

import org.apache.commons.lang.StringUtils;

public enum MyBatisTag {

    MAPPER("mapper", "namespace"),
    SELECT("select", "id"),
    INSERT("insert", "id"),
    UPDATE("update", "id"),
    DELETE("delete", "id");

    private String value;

    private String majorAttribute;

    MyBatisTag(String value, String majorAttribute) {
        this.value = value;
        this.majorAttribute = majorAttribute;
    }

    public static boolean isValue(String value, String majorAttribute) {
        if (StringUtils.isBlank(value) || StringUtils.isBlank(majorAttribute)) {
            return false;
        }
        MyBatisTag[] myBatisTags = MyBatisTag.values();
        for (MyBatisTag myBatisTag : myBatisTags) {
            if (StringUtils.equals(myBatisTag.getValue(), value) && StringUtils.equals(myBatisTag.getMajorAttribute(), majorAttribute)) {
                return true;
            }
        }
        return false;
    }

    public String getValue() {
        return value;
    }

    public String getMajorAttribute() {
        return majorAttribute;
    }
}
