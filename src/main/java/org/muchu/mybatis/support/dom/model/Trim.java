// Generated on Mon Oct 26 16:46:53 CST 2020
// DTD/Schema  :    mybatis-3-mapper.dtd

package org.muchu.mybatis.support.dom.model;

import com.intellij.util.xml.*;
import com.intellij.util.xml.DomElement;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * mybatis-3-mapper.dtd:trim interface.
 */
public interface Trim extends DomElement {

	/**
	 * Returns the value of the simple content.
	 * @return the value of the simple content.
	 */
	@NotNull
	String getValue();
	/**
	 * Sets the value of the simple content.
	 * @param value the new value to set
	 */
	void setValue(String value);


	/**
	 * Returns the value of the suffixOverrides child.
	 * Attribute suffixOverrides
	 * @return the value of the suffixOverrides child.
	 */
	@NotNull
	GenericAttributeValue<String> getSuffixOverrides();


	/**
	 * Returns the value of the prefix child.
	 * Attribute prefix
	 * @return the value of the prefix child.
	 */
	@NotNull
	GenericAttributeValue<String> getPrefix();


	/**
	 * Returns the value of the suffix child.
	 * Attribute suffix
	 * @return the value of the suffix child.
	 */
	@NotNull
	GenericAttributeValue<String> getSuffix();


	/**
	 * Returns the value of the prefixOverrides child.
	 * Attribute prefixOverrides
	 * @return the value of the prefixOverrides child.
	 */
	@NotNull
	GenericAttributeValue<String> getPrefixOverrides();


	/**
	 * Returns the list of include children.
	 * Type include documentation
	 * <pre>
	 *  Dynamic 
	 * </pre>
	 * @return the list of include children.
	 */
	@NotNull
	List<Include> getIncludes();
	/**
	 * Adds new child to the list of include children.
	 * @return created child
	 */
	Include addInclude();


	/**
	 * Returns the list of trim children.
	 * @return the list of trim children.
	 */
	@NotNull
	List<Trim> getTrims();
	/**
	 * Adds new child to the list of trim children.
	 * @return created child
	 */
	Trim addTrim();


	/**
	 * Returns the list of where children.
	 * @return the list of where children.
	 */
	@NotNull
	List<Where> getWheres();
	/**
	 * Adds new child to the list of where children.
	 * @return created child
	 */
	Where addWhere();


	/**
	 * Returns the list of set children.
	 * @return the list of set children.
	 */
	@NotNull
	List<Set> getSets();
	/**
	 * Adds new child to the list of set children.
	 * @return created child
	 */
	Set addSet();


	/**
	 * Returns the list of foreach children.
	 * @return the list of foreach children.
	 */
	@NotNull
	List<Foreach> getForeaches();
	/**
	 * Adds new child to the list of foreach children.
	 * @return created child
	 */
	Foreach addForeach();


	/**
	 * Returns the list of choose children.
	 * @return the list of choose children.
	 */
	@NotNull
	List<Choose> getChooses();
	/**
	 * Adds new child to the list of choose children.
	 * @return created child
	 */
	Choose addChoose();


	/**
	 * Returns the list of if children.
	 * @return the list of if children.
	 */
	@NotNull
	List<If> getIfs();
	/**
	 * Adds new child to the list of if children.
	 * @return created child
	 */
	If addIf();


	/**
	 * Returns the list of bind children.
	 * @return the list of bind children.
	 */
	@NotNull
	List<Bind> getBinds();
	/**
	 * Adds new child to the list of bind children.
	 * @return created child
	 */
	Bind addBind();


}
