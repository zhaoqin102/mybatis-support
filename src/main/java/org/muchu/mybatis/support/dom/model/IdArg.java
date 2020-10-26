// Generated on Mon Oct 26 16:46:53 CST 2020
// DTD/Schema  :    mybatis-3-mapper.dtd

package org.muchu.mybatis.support.dom.model;

import com.intellij.util.xml.*;
import com.intellij.util.xml.DomElement;
import org.jetbrains.annotations.NotNull;

/**
 * mybatis-3-mapper.dtd:idArg interface.
 */
public interface IdArg extends DomElement {

	/**
	 * Returns the value of the select child.
	 * Attribute select
	 * @return the value of the select child.
	 */
	@NotNull
	GenericAttributeValue<String> getSelect();


	/**
	 * Returns the value of the name child.
	 * Attribute name
	 * @return the value of the name child.
	 */
	@NotNull
	GenericAttributeValue<String> getName();


	/**
	 * Returns the value of the resultMap child.
	 * Attribute resultMap
	 * @return the value of the resultMap child.
	 */
	@NotNull
	GenericAttributeValue<String> getResultMap();


	/**
	 * Returns the value of the columnPrefix child.
	 * Attribute columnPrefix
	 * @return the value of the columnPrefix child.
	 */
	@NotNull
	GenericAttributeValue<String> getColumnPrefix();


	/**
	 * Returns the value of the jdbcType child.
	 * Attribute jdbcType
	 * @return the value of the jdbcType child.
	 */
	@NotNull
	GenericAttributeValue<String> getJdbcType();


	/**
	 * Returns the value of the column child.
	 * Attribute column
	 * @return the value of the column child.
	 */
	@NotNull
	GenericAttributeValue<String> getColumn();


	/**
	 * Returns the value of the typeHandler child.
	 * Attribute typeHandler
	 * @return the value of the typeHandler child.
	 */
	@NotNull
	GenericAttributeValue<String> getTypeHandler();


	/**
	 * Returns the value of the javaType child.
	 * Attribute javaType
	 * @return the value of the javaType child.
	 */
	@NotNull
	GenericAttributeValue<String> getJavaType();


}
