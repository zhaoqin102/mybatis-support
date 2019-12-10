package org.muchu.mybatis.support.bean;

import com.intellij.util.xml.Attribute;
import com.intellij.util.xml.GenericAttributeValue;

public interface ParameterType {

    @Attribute("parameterType")
    GenericAttributeValue<String> getParameterType();
}
