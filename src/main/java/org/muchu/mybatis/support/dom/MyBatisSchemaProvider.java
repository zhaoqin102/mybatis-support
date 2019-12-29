package org.muchu.mybatis.support.dom;

import com.intellij.javaee.ResourceRegistrar;
import com.intellij.javaee.StandardResourceProvider;
import org.muchu.mybatis.support.constant.MyBatisDoctype;

public class MyBatisSchemaProvider implements StandardResourceProvider {

    @Override
    public void registerResources(ResourceRegistrar registrar) {
        registrar.addStdResource(MyBatisDoctype.SYSTEM_ID, "/schemas/mybatis-3-mapper.dtd", getClass());
    }
}
