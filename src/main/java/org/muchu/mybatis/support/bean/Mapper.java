package org.muchu.mybatis.support.bean;

import com.intellij.util.xml.*;

import java.util.List;

public interface Mapper extends DomElement {

    @Attribute("namespace")
    GenericAttributeValue<String> getNameSpace();

    @SubTagList("resultMap")
    List<ResultMap> getResultMaps();

    @SubTagsList({"select", "insert", "update", "delete"})
    List<Statement> getStatements();

    @SubTagList("sql")
    List<Sql> getSQL();

    List<Select> getSelects();

    List<Insert> getInserts();

    List<Update> getUpdates();

    List<Delete> getDeletes();

}
