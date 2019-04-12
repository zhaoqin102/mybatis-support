package org.muchu.mybatis.support.constant;

/**
 * @author heber
 */
public enum MyBatisTag {

    MAPPER("mapper");

    private String value;

    MyBatisTag(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
