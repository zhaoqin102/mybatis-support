// Generated on Mon Oct 26 16:46:53 CST 2020
// DTD/Schema  :    mybatis-3-mapper.dtd

package org.muchu.mybatis.support.dom.model;

import com.intellij.util.xml.*;
import com.intellij.util.xml.DomElement;
import org.jetbrains.annotations.NotNull;

/**
 * mybatis-3-mapper.dtd:parameter interface.
 */
public interface Parameter extends DomElement {

	/**
	 * Returns the value of the resultMap child.
	 * Attribute resultMap
	 * @return the value of the resultMap child.
	 */
	@NotNull
	GenericAttributeValue<String> getResultMap();


	/**
	 * Returns the value of the jdbcType child.
	 * Attribute jdbcType
	 * @return the value of the jdbcType child.
	 */
	@NotNull
	GenericAttributeValue<String> getJdbcType();


	/**
	 * Returns the value of the property child.
	 * Attribute property
	 * @return the value of the property child.
	 */
	@NotNull
	@Required
	GenericAttributeValue<String> getProperty();


	/**
	 * Returns the value of the typeHandler child.
	 * Attribute typeHandler
	 * @return the value of the typeHandler child.
	 */
	@NotNull
	GenericAttributeValue<String> getTypeHandler();


	/**
	 * Returns the value of the scale child.
	 * Attribute scale
	 * @return the value of the scale child.
	 */
	@NotNull
	GenericAttributeValue<String> getScale();


	/**
	 * Returns the value of the mode child.
	 * Attribute mode
	 * @return the value of the mode child.
	 */
	@NotNull
	GenericAttributeValue<String> getMode();


	/**
	 * Returns the value of the javaType child.
	 * Attribute javaType
	 * @return the value of the javaType child.
	 */
	@NotNull
	GenericAttributeValue<String> getJavaType();


}
