package org.muchu.mybatis.support.bean;

import com.intellij.util.xml.Attribute;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericAttributeValue;

public interface Include extends DomElement {

    @Attribute("refid")
    GenericAttributeValue<String> getRefId();
}
