// Generated on Mon Oct 26 16:46:53 CST 2020
// DTD/Schema  :    mybatis-3-mapper.dtd

package org.muchu.mybatis.support.dom.model;

import com.intellij.util.xml.GenericAttributeValue;
import org.jetbrains.annotations.NotNull;

/**
 * mybatis-3-mapper.dtd:delete interface.
 */
public interface Delete extends Statement {


    /**
     * Returns the value of the databaseId child.
     * Attribute databaseId
     *
     * @return the value of the databaseId child.
     */
    @NotNull
    GenericAttributeValue<String> getDatabaseId();


    /**
     * Returns the value of the timeout child.
     * Attribute timeout
     *
     * @return the value of the timeout child.
     */
    @NotNull
    GenericAttributeValue<String> getTimeout();


    /**
     * Returns the value of the lang child.
     * Attribute lang
     *
     * @return the value of the lang child.
     */
    @NotNull
    GenericAttributeValue<String> getLang();


    /**
     * Returns the value of the flushCache child.
     * Attribute flushCache
     *
     * @return the value of the flushCache child.
     */
    @NotNull
    GenericAttributeValue<String> getFlushCache();


    /**
     * Returns the list of trim children.
     *
     * @return the list of trim children.
     */
    @NotNull
    java.util.List<Trim> getTrims();

    /**
     * Adds new child to the list of trim children.
     *
     * @return created child
     */
    Trim addTrim();


    /**
     * Returns the list of where children.
     *
     * @return the list of where children.
     */
    @NotNull
    java.util.List<Where> getWheres();

    /**
     * Adds new child to the list of where children.
     *
     * @return created child
     */
    Where addWhere();


    /**
     * Returns the list of set children.
     *
     * @return the list of set children.
     */
    @NotNull
    java.util.List<Set> getSets();

    /**
     * Adds new child to the list of set children.
     *
     * @return created child
     */
    Set addSet();


    /**
     * Returns the list of foreach children.
     *
     * @return the list of foreach children.
     */
    @NotNull
    java.util.List<Foreach> getForeaches();

    /**
     * Adds new child to the list of foreach children.
     *
     * @return created child
     */
    Foreach addForeach();


    /**
     * Returns the list of choose children.
     *
     * @return the list of choose children.
     */
    @NotNull
    java.util.List<Choose> getChooses();

    /**
     * Adds new child to the list of choose children.
     *
     * @return created child
     */
    Choose addChoose();


    /**
     * Returns the list of if children.
     *
     * @return the list of if children.
     */
    @NotNull
    java.util.List<If> getIfs();

    /**
     * Adds new child to the list of if children.
     *
     * @return created child
     */
    If addIf();


    /**
     * Returns the list of bind children.
     *
     * @return the list of bind children.
     */
    @NotNull
    java.util.List<Bind> getBinds();

    /**
     * Adds new child to the list of bind children.
     *
     * @return created child
     */
    Bind addBind();


}
