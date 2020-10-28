// Generated on Mon Oct 26 16:46:53 CST 2020
// DTD/Schema  :    mybatis-3-mapper.dtd

package org.muchu.mybatis.support.dom.model;

import com.intellij.util.xml.GenericAttributeValue;
import com.intellij.util.xml.SubTagList;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * mybatis-3-mapper.dtd:insert interface.
 */
public interface Insert extends Statement {

    /**
     * Returns the value of the keyColumn child.
     * Attribute keyColumn
     *
     * @return the value of the keyColumn child.
     */
    @NotNull
    GenericAttributeValue<String> getKeyColumn();


    /**
     * Returns the value of the lang child.
     * Attribute lang
     *
     * @return the value of the lang child.
     */
    @NotNull
    GenericAttributeValue<String> getLang();


    /**
     * Returns the value of the keyProperty child.
     * Attribute keyProperty
     *
     * @return the value of the keyProperty child.
     */
    @NotNull
    GenericAttributeValue<String> getKeyProperty();


    /**
     * Returns the value of the databaseId child.
     * Attribute databaseId
     *
     * @return the value of the databaseId child.
     */
    @NotNull
    GenericAttributeValue<String> getDatabaseId();


    /**
     * Returns the value of the flushCache child.
     * Attribute flushCache
     *
     * @return the value of the flushCache child.
     */
    @NotNull
    GenericAttributeValue<String> getFlushCache();


    /**
     * Returns the value of the timeout child.
     * Attribute timeout
     *
     * @return the value of the timeout child.
     */
    @NotNull
    GenericAttributeValue<String> getTimeout();


    /**
     * Returns the value of the useGeneratedKeys child.
     * Attribute useGeneratedKeys
     *
     * @return the value of the useGeneratedKeys child.
     */
    @NotNull
    GenericAttributeValue<String> getUseGeneratedKeys();


    /**
     * Returns the list of selectKey children.
     *
     * @return the list of selectKey children.
     */
    @NotNull
    @SubTagList("selectKey")
    List<SelectKey> getSelectKeys();

    /**
     * Adds new child to the list of selectKey children.
     *
     * @return created child
     */
    @SubTagList("selectKey")
    SelectKey addSelectKey();

    /**
     * Returns the list of trim children.
     *
     * @return the list of trim children.
     */
    @NotNull
    List<Trim> getTrims();

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
    List<Where> getWheres();

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
    List<Set> getSets();

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
    List<Foreach> getForeaches();

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
    List<Choose> getChooses();

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
    List<If> getIfs();

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
    List<Bind> getBinds();

    /**
     * Adds new child to the list of bind children.
     *
     * @return created child
     */
    Bind addBind();


}
