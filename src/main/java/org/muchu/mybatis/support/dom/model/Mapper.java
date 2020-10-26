// Generated on Mon Oct 26 16:46:53 CST 2020
// DTD/Schema  :    mybatis-3-mapper.dtd

package org.muchu.mybatis.support.dom.model;

import com.intellij.util.xml.*;
import com.intellij.util.xml.DomElement;
import org.jetbrains.annotations.NotNull;
import org.muchu.mybatis.support.dom.MyBatisDomConstants;

import java.util.List;

/**
 * mybatis-3-mapper.dtd:mapper interface.
 * Type mapper documentation
 * <pre>
 *        Copyright 2009-2018 the original author or authors.
 *        Licensed under the Apache License, Version 2.0 (the "License");
 *        you may not use this file except in compliance with the License.
 *        You may obtain a copy of the License at
 *           http://www.apache.org/licenses/LICENSE-2.0
 *        Unless required by applicable law or agreed to in writing, software
 *        distributed under the License is distributed on an "AS IS" BASIS,
 *        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *        See the License for the specific language governing permissions and
 *        limitations under the License.
 * </pre>
 */
@Namespace(MyBatisDomConstants.MYBATIS_NAMESPACE_KEY)
public interface Mapper extends DomElement {
    String TAG_NAME = "mapper";

    /**
     * Returns the value of the namespace child.
     * Attribute namespace
     *
     * @return the value of the namespace child.
     */
    @NotNull
    GenericAttributeValue<String> getNamespace();


    /**
     * Returns the list of cache-ref children.
     *
     * @return the list of cache-ref children.
     */
    @NotNull
    java.util.List<CacheRef> getCacheRefs();

    /**
     * Adds new child to the list of cache-ref children.
     *
     * @return created child
     */
    CacheRef addCacheRef();


    /**
     * Returns the list of cache children.
     *
     * @return the list of cache children.
     */
    @NotNull
    java.util.List<Cache> getCaches();

    /**
     * Adds new child to the list of cache children.
     *
     * @return created child
     */
    Cache addCache();


    /**
     * Returns the list of resultMap children.
     *
     * @return the list of resultMap children.
     */
    @NotNull
    @SubTagList("resultMap")
    java.util.List<ResultMap> getResultMaps();

    /**
     * Adds new child to the list of resultMap children.
     *
     * @return created child
     */
    @SubTagList("resultMap")
    ResultMap addResultMap();


    /**
     * Returns the list of parameterMap children.
     *
     * @return the list of parameterMap children.
     */
    @NotNull
    @SubTagList("parameterMap")
    java.util.List<ParameterMap> getParameterMaps();

    /**
     * Adds new child to the list of parameterMap children.
     *
     * @return created child
     */
    @SubTagList("parameterMap")
    ParameterMap addParameterMap();


    /**
     * Returns the list of sql children.
     *
     * @return the list of sql children.
     */
    @NotNull
    java.util.List<Sql> getSqls();

    /**
     * Adds new child to the list of sql children.
     *
     * @return created child
     */
    Sql addSql();


    /**
     * Returns the list of insert children.
     *
     * @return the list of insert children.
     */
    @NotNull
    java.util.List<Insert> getInserts();

    /**
     * Adds new child to the list of insert children.
     *
     * @return created child
     */
    Insert addInsert();


    /**
     * Returns the list of update children.
     *
     * @return the list of update children.
     */
    @NotNull
    java.util.List<Update> getUpdates();

    /**
     * Adds new child to the list of update children.
     *
     * @return created child
     */
    Update addUpdate();


    /**
     * Returns the list of delete children.
     *
     * @return the list of delete children.
     */
    @NotNull
    java.util.List<Delete> getDeletes();

    /**
     * Adds new child to the list of delete children.
     *
     * @return created child
     */
    Delete addDelete();


    /**
     * Returns the list of select children.
     *
     * @return the list of select children.
     */
    @NotNull
    java.util.List<Select> getSelects();

    /**
     * Adds new child to the list of select children.
     *
     * @return created child
     */
    Select addSelect();

    @SubTagsList({"select", "insert", "update", "delete"})
    List<Statement> getStatements();
}
