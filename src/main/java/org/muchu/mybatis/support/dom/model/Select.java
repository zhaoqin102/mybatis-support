package org.muchu.mybatis.support.dom.model;

import com.intellij.util.xml.Attribute;
import com.intellij.util.xml.GenericAttributeValue;

public interface Select extends Statement {

    @Attribute("resultType")
    GenericAttributeValue<String> getResultType();

    @Attribute("resultMap")
    GenericAttributeValue<String> getResultMap();

    @Attribute("useCache")
    GenericAttributeValue<String> getUseCache();

    @Attribute("fetchSize")
    GenericAttributeValue<String> getFetchSize();

    @Attribute("resultSetType")
    GenericAttributeValue<String> getResultSetType();

}
