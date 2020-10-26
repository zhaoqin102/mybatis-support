// Generated on Mon Oct 26 16:46:53 CST 2020
// DTD/Schema  :    mybatis-3-mapper.dtd

package org.muchu.mybatis.support.dom.model;

import com.intellij.util.xml.*;
import com.intellij.util.xml.DomElement;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * mybatis-3-mapper.dtd:include interface.
 * Type include documentation
 * <pre>
 *  Dynamic 
 * </pre>
 */
public interface Include extends DomElement {

	/**
	 * Returns the value of the refid child.
	 * Attribute refid
	 * @return the value of the refid child.
	 */
	@NotNull
	@Required
	GenericAttributeValue<String> getRefid();


	/**
	 * Returns the list of property children.
	 * @return the list of property children.
	 */
	@NotNull
	List<Property> getProperties();
	/**
	 * Adds new child to the list of property children.
	 * @return created child
	 */
	Property addProperty();


}
