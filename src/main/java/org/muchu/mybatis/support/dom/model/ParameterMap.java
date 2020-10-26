// Generated on Mon Oct 26 16:46:53 CST 2020
// DTD/Schema  :    mybatis-3-mapper.dtd

package org.muchu.mybatis.support.dom.model;

import com.intellij.util.xml.*;
import com.intellij.util.xml.DomElement;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * mybatis-3-mapper.dtd:parameterMap interface.
 */
public interface ParameterMap extends DomElement {

	/**
	 * Returns the value of the type child.
	 * Attribute type
	 * @return the value of the type child.
	 */
	@NotNull
	@Required
	GenericAttributeValue<String> getType();


	/**
	 * Returns the value of the id child.
	 * Attribute id
	 * @return the value of the id child.
	 */
	@NotNull
	@Required
	GenericAttributeValue<String> getId();


	/**
	 * Returns the list of parameter children.
	 * @return the list of parameter children.
	 */
	@NotNull
	List<Parameter> getParameters();
	/**
	 * Adds new child to the list of parameter children.
	 * @return created child
	 */
	Parameter addParameter();


}
