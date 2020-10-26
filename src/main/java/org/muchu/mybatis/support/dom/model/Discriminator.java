// Generated on Mon Oct 26 16:46:53 CST 2020
// DTD/Schema  :    mybatis-3-mapper.dtd

package org.muchu.mybatis.support.dom.model;

import com.intellij.util.xml.*;
import com.intellij.util.xml.DomElement;
import org.jetbrains.annotations.NotNull;

/**
 * mybatis-3-mapper.dtd:discriminator interface.
 */
public interface Discriminator extends DomElement {

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
	@Required
	GenericAttributeValue<String> getJavaType();


	/**
	 * Returns the list of case children.
	 * @return the list of case children.
	 */
	@NotNull
	java.util.List<Case> getCases();
	/**
	 * Adds new child to the list of case children.
	 * @return created child
	 */
	Case addCase();


}
