// Generated on Mon Oct 26 16:46:53 CST 2020
// DTD/Schema  :    mybatis-3-mapper.dtd

package org.muchu.mybatis.support.dom.model;

import com.intellij.util.xml.*;
import com.intellij.util.xml.DomElement;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * mybatis-3-mapper.dtd:constructor interface.
 */
public interface Constructor extends DomElement {

	/**
	 * Returns the list of idArg children.
	 * @return the list of idArg children.
	 */
	@NotNull
	@SubTagList ("idArg")
	List<IdArg> getIdArgs();
	/**
	 * Adds new child to the list of idArg children.
	 * @return created child
	 */
	@SubTagList ("idArg")
	IdArg addIdArg();


	/**
	 * Returns the list of arg children.
	 * @return the list of arg children.
	 */
	@NotNull
	List<Arg> getArgs();
	/**
	 * Adds new child to the list of arg children.
	 * @return created child
	 */
	Arg addArg();


}
