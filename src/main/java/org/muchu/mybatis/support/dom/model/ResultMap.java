package org.muchu.mybatis.support.dom.model;

import com.intellij.util.xml.Attribute;
import com.intellij.util.xml.GenericAttributeValue;

public interface ResultMap extends MybatisId {

    @Attribute("type")
    GenericAttributeValue<String> getType();

    @Attribute("extends")
    GenericAttributeValue<String> getExtends();

    @Attribute("autoMapping")
    GenericAttributeValue<String> getAutoMapping();
}
