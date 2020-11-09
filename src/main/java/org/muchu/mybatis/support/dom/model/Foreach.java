// Generated on Mon Oct 26 16:46:53 CST 2020
// DTD/Schema  :    mybatis-3-mapper.dtd

package org.muchu.mybatis.support.dom.model;

import com.intellij.util.xml.GenericAttributeValue;
import com.intellij.util.xml.Required;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * mybatis-3-mapper.dtd:foreach interface.
 */
public interface Foreach extends MyBatisDomElement {

  /**
   * Returns the value of the simple content.
   *
   * @return the value of the simple content.
   */
  @NotNull
  String getValue();

  /**
   * Sets the value of the simple content.
   *
   * @param value the new value to set
   */
  void setValue(String value);


  /**
   * Returns the value of the open child.
   * Attribute open
   *
   * @return the value of the open child.
   */
  @NotNull
  GenericAttributeValue<String> getOpen();


  /**
   * Returns the value of the index child.
   * Attribute index
   *
   * @return the value of the index child.
   */
  @NotNull
  GenericAttributeValue<String> getIndex();


  /**
   * Returns the value of the item child.
   * Attribute item
   *
   * @return the value of the item child.
   */
  @NotNull
  GenericAttributeValue<String> getItem();


  /**
   * Returns the value of the separator child.
   * Attribute separator
   *
   * @return the value of the separator child.
   */
  @NotNull
  GenericAttributeValue<String> getSeparator();


  /**
   * Returns the value of the close child.
   * Attribute close
   *
   * @return the value of the close child.
   */
  @NotNull
  GenericAttributeValue<String> getClose();


  /**
   * Returns the value of the collection child.
   * Attribute collection
   *
   * @return the value of the collection child.
   */
  @NotNull
  @Required
  GenericAttributeValue<String> getCollection();


  /**
   * Returns the list of include children.
   * Type include documentation
   * <pre>
   *  Dynamic
   * </pre>
   *
   * @return the list of include children.
   */
  @NotNull
  List<Include> getIncludes();

  /**
   * Adds new child to the list of include children.
   *
   * @return created child
   */
  Include addInclude();


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
