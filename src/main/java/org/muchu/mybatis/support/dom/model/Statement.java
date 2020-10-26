package org.muchu.mybatis.support.dom.model;

import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericAttributeValue;
import com.intellij.util.xml.Required;
import org.jetbrains.annotations.NotNull;

public interface Statement extends DomElement {

    /**
     * Returns the value of the simple content.
     *
     * @return the value of the simple content.
     */
    @NotNull
    String getValue();

    /**
     * Sets the value of the simple content.
     *
     * @param value the new value to set
     */
    void setValue(String value);


    /**
     * Returns the value of the id child.
     * Attribute id
     *
     * @return the value of the id child.
     */
    @NotNull
    @Required
    GenericAttributeValue<String> getId();

    /**
     * Returns the list of include children.
     * Type include documentation
     * <pre>
     *  Dynamic
     * </pre>
     *
     * @return the list of include children.
     */
    @NotNull
    java.util.List<Include> getIncludes();

}
