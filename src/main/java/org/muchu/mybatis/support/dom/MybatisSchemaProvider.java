package org.muchu.mybatis.support.dom;

import com.intellij.javaee.ResourceRegistrar;
import com.intellij.javaee.StandardResourceProvider;

public class MybatisSchemaProvider implements StandardResourceProvider {

    @Override
    public void registerResources(ResourceRegistrar registrar) {
        registrar.addStdResource("http://mybatis.org/dtd/mybatis-3-mapper.dtd", "/schemas/mybatis-3-mapper.dtd", getClass());
    }
}
