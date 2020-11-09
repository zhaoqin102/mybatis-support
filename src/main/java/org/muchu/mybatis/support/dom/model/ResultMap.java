// Generated on Mon Oct 26 16:46:53 CST 2020
// DTD/Schema  :    mybatis-3-mapper.dtd

package org.muchu.mybatis.support.dom.model;

import com.intellij.util.xml.GenericAttributeValue;
import com.intellij.util.xml.Required;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * mybatis-3-mapper.dtd:resultMap interface.
 */
public interface ResultMap extends MyBatisDomElement {

  /**
   * Returns the value of the autoMapping child.
   * Attribute autoMapping
   *
   * @return the value of the autoMapping child.
   */
  @NotNull
  GenericAttributeValue<String> getAutoMapping();


  /**
   * Returns the value of the type child.
   * Attribute type
   *
   * @return the value of the type child.
   */
  @NotNull
  @Required
  GenericAttributeValue<String> getType();


  /**
   * Returns the value of the extends child.
   * Attribute extends
   *
   * @return the value of the extends child.
   */
  @NotNull
  GenericAttributeValue<String> getExtends();


  /**
   * Returns the value of the id child.
   * Attribute id
   *
   * @return the value of the id child.
   */
  @NotNull
  @Required
  GenericAttributeValue<String> getId();


  /**
   * Returns the value of the constructor child.
   *
   * @return the value of the constructor child.
   */
  @NotNull
  Constructor getConstructor();


  /**
   * Returns the list of id children.
   *
   * @return the list of id children.
   */
  @NotNull
  List<Id> getIds();

  /**
   * Adds new child to the list of id children.
   *
   * @return created child
   */
  Id addId();


  /**
   * Returns the list of result children.
   *
   * @return the list of result children.
   */
  @NotNull
  List<Result> getResults();

  /**
   * Adds new child to the list of result children.
   *
   * @return created child
   */
  Result addResult();


  /**
   * Returns the list of association children.
   *
   * @return the list of association children.
   */
  @NotNull
  List<Association> getAssociations();

  /**
   * Adds new child to the list of association children.
   *
   * @return created child
   */
  Association addAssociation();


  /**
   * Returns the list of collection children.
   *
   * @return the list of collection children.
   */
  @NotNull
  List<Collection> getCollections();

  /**
   * Adds new child to the list of collection children.
   *
   * @return created child
   */
  Collection addCollection();


  /**
   * Returns the value of the discriminator child.
   *
   * @return the value of the discriminator child.
   */
  @NotNull
  Discriminator getDiscriminator();


}
