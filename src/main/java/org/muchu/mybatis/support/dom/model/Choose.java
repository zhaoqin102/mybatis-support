// Generated on Mon Oct 26 16:46:53 CST 2020
// DTD/Schema  :    mybatis-3-mapper.dtd

package org.muchu.mybatis.support.dom.model;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * mybatis-3-mapper.dtd:choose interface.
 */
public interface Choose extends MyBatisDomElement {

  /**
   * Returns the list of when children.
   *
   * @return the list of when children.
   */
  @NotNull
  List<When> getWhens();

  /**
   * Adds new child to the list of when children.
   *
   * @return created child
   */
  When addWhen();


  /**
   * Returns the value of the otherwise child.
   *
   * @return the value of the otherwise child.
   */
  @NotNull
  Otherwise getOtherwise();


}
