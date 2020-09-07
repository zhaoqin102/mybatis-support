package org.muchu.mybatis.support.dom.model;

import com.intellij.util.xml.Attribute;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericAttributeValue;
import com.intellij.util.xml.NameValue;

public interface MybatisId extends DomElement {

    @Attribute("id")
    GenericAttributeValue<String> getId();
}
