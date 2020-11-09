// Generated on Mon Oct 26 16:46:53 CST 2020
// DTD/Schema  :    mybatis-3-mapper.dtd

package org.muchu.mybatis.support.dom.model;

import com.intellij.util.xml.GenericAttributeValue;
import com.intellij.util.xml.Required;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * mybatis-3-mapper.dtd:association interface.
 */
public interface Association extends MyBatisDomElement {

  /**
   * Returns the value of the foreignColumn child.
   * Attribute foreignColumn
   *
   * @return the value of the foreignColumn child.
   */
  @NotNull
  GenericAttributeValue<String> getForeignColumn();


  /**
   * Returns the value of the resultMap child.
   * Attribute resultMap
   *
   * @return the value of the resultMap child.
   */
  @NotNull
  GenericAttributeValue<String> getResultMap();


  /**
   * Returns the value of the typeHandler child.
   * Attribute typeHandler
   *
   * @return the value of the typeHandler child.
   */
  @NotNull
  GenericAttributeValue<String> getTypeHandler();


  /**
   * Returns the value of the fetchType child.
   * Attribute fetchType
   *
   * @return the value of the fetchType child.
   */
  @NotNull
  GenericAttributeValue<String> getFetchType();


  /**
   * Returns the value of the columnPrefix child.
   * Attribute columnPrefix
   *
   * @return the value of the columnPrefix child.
   */
  @NotNull
  GenericAttributeValue<String> getColumnPrefix();


  /**
   * Returns the value of the column child.
   * Attribute column
   *
   * @return the value of the column child.
   */
  @NotNull
  GenericAttributeValue<String> getColumn();


  /**
   * Returns the value of the javaType child.
   * Attribute javaType
   *
   * @return the value of the javaType child.
   */
  @NotNull
  GenericAttributeValue<String> getJavaType();


  /**
   * Returns the value of the autoMapping child.
   * Attribute autoMapping
   *
   * @return the value of the autoMapping child.
   */
  @NotNull
  GenericAttributeValue<String> getAutoMapping();


  /**
   * Returns the value of the jdbcType child.
   * Attribute jdbcType
   *
   * @return the value of the jdbcType child.
   */
  @NotNull
  GenericAttributeValue<String> getJdbcType();


  /**
   * Returns the value of the resultSet child.
   * Attribute resultSet
   *
   * @return the value of the resultSet child.
   */
  @NotNull
  GenericAttributeValue<String> getResultSet();


  /**
   * Returns the value of the select child.
   * Attribute select
   *
   * @return the value of the select child.
   */
  @NotNull
  GenericAttributeValue<String> getSelect();


  /**
   * Returns the value of the property child.
   * Attribute property
   *
   * @return the value of the property child.
   */
  @NotNull
  @Required
  GenericAttributeValue<String> getProperty();


  /**
   * Returns the value of the notNullColumn child.
   * Attribute notNullColumn
   *
   * @return the value of the notNullColumn child.
   */
  @NotNull
  GenericAttributeValue<String> getNotNullColumn();


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
  java.util.List<Id> getIds();

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
  java.util.List<Result> getResults();

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
