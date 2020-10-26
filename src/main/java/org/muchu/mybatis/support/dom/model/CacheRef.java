// Generated on Mon Oct 26 16:46:53 CST 2020
// DTD/Schema  :    mybatis-3-mapper.dtd

package org.muchu.mybatis.support.dom.model;

import com.intellij.util.xml.*;
import com.intellij.util.xml.DomElement;
import org.jetbrains.annotations.NotNull;

/**
 * mybatis-3-mapper.dtd:cache-ref interface.
 */
public interface CacheRef extends DomElement {

	/**
	 * Returns the value of the namespace child.
	 * Attribute namespace
	 * @return the value of the namespace child.
	 */
	@NotNull
	@Required
	GenericAttributeValue<String> getNamespace();


}
