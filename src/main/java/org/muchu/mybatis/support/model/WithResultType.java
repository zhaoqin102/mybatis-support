package org.muchu.mybatis.support.model;

import com.intellij.util.xml.Attribute;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericAttributeValue;
import com.intellij.util.xml.NameValue;

public interface WithResultType extends DomElement {

    @NameValue
    @Attribute("resultType")
    GenericAttributeValue<String> getResultType();
}
