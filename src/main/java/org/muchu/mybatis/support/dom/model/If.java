package org.muchu.mybatis.support.dom.model;

import com.intellij.util.xml.Attribute;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericAttributeValue;

public interface If extends DomElement {

    @Attribute("test")
    GenericAttributeValue<String> getTest();


}
