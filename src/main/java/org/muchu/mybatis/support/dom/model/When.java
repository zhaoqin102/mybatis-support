// Generated on Mon Oct 26 16:46:53 CST 2020
// DTD/Schema  :    mybatis-3-mapper.dtd

package org.muchu.mybatis.support.dom.model;

import com.intellij.util.xml.*;
import com.intellij.util.xml.DomElement;
import org.jetbrains.annotations.NotNull;

/**
 * mybatis-3-mapper.dtd:when interface.
 */
public interface When extends DomElement {

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
	 * Returns the value of the test child.
	 * Attribute test
	 * @return the value of the test child.
	 */
	@NotNull
	@Required
	GenericAttributeValue<String> getTest();


	/**
	 * Returns the list of include children.
	 * Type include documentation
	 * <pre>
	 *  Dynamic 
	 * </pre>
	 * @return the list of include children.
	 */
	@NotNull
	java.util.List<Include> getIncludes();
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
	java.util.List<Trim> getTrims();
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
	java.util.List<Where> getWheres();
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
	java.util.List<Set> getSets();
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
	java.util.List<Foreach> getForeaches();
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
	java.util.List<Choose> getChooses();
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
	java.util.List<If> getIfs();
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
	java.util.List<Bind> getBinds();
	/**
	 * Adds new child to the list of bind children.
	 * @return created child
	 */
	Bind addBind();


}
