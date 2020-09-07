package org.muchu.mybatis.support.dom.model;

import com.intellij.util.xml.Attribute;
import com.intellij.util.xml.GenericAttributeValue;

import java.util.List;

public interface Statement extends MybatisId {

    @Attribute("parameterType")
    GenericAttributeValue<String> getParameterType();

    @Attribute("flushCache")
    GenericAttributeValue<String> getFlushCache();

    @Attribute("timeout")
    GenericAttributeValue<String> getTimeout();

    @Attribute("statementType")
    GenericAttributeValue<String> getStatementType();

    List<Include> getIncludes();
}
