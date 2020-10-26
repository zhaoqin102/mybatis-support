// Generated on Mon Oct 26 16:46:53 CST 2020
// DTD/Schema  :    mybatis-3-mapper.dtd

package org.muchu.mybatis.support.dom.model;

import com.intellij.util.xml.*;
import com.intellij.util.xml.DomElement;
import org.jetbrains.annotations.NotNull;

/**
 * mybatis-3-mapper.dtd:cache interface.
 */
public interface Cache extends DomElement {

	/**
	 * Returns the value of the size child.
	 * Attribute size
	 * @return the value of the size child.
	 */
	@NotNull
	GenericAttributeValue<String> getSize();


	/**
	 * Returns the value of the flushInterval child.
	 * Attribute flushInterval
	 * @return the value of the flushInterval child.
	 */
	@NotNull
	GenericAttributeValue<String> getFlushInterval();


	/**
	 * Returns the value of the type child.
	 * Attribute type
	 * @return the value of the type child.
	 */
	@NotNull
	GenericAttributeValue<String> getType();


	/**
	 * Returns the value of the blocking child.
	 * Attribute blocking
	 * @return the value of the blocking child.
	 */
	@NotNull
	GenericAttributeValue<String> getBlocking();


	/**
	 * Returns the value of the eviction child.
	 * Attribute eviction
	 * @return the value of the eviction child.
	 */
	@NotNull
	GenericAttributeValue<String> getEviction();


	/**
	 * Returns the value of the readOnly child.
	 * Attribute readOnly
	 * @return the value of the readOnly child.
	 */
	@NotNull
	GenericAttributeValue<String> getReadOnly();


	/**
	 * Returns the list of property children.
	 * @return the list of property children.
	 */
	@NotNull
	java.util.List<Property> getProperties();
	/**
	 * Adds new child to the list of property children.
	 * @return created child
	 */
	Property addProperty();


}
