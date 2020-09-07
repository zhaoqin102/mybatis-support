package org.muchu.mybatis.support.dom;

import com.intellij.util.xml.*;
import org.muchu.mybatis.support.dom.MyBatisDomConstants;
import org.muchu.mybatis.support.dom.model.*;

import java.util.List;

@Namespace(MyBatisDomConstants.MYBATIS_NAMESPACE_KEY)
public interface MyBatisRoot extends DomElement {

    String TAG_NAME = "mybatis";

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
