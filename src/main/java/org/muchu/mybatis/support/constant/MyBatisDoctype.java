package org.muchu.mybatis.support.constant;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.DocumentType;

public class MyBatisDoctype {

    public static final String NAME = "mapper";

    public static final String SYSTEM_ID = "http://mybatis.org/dtd/mybatis-3-mapper.dtd";

    public static final String PUBLIC_ID = "-//mybatis.org//DTD Mapper 3.0//EN";

    public static boolean isMyBatisDoctype(DocumentType doctype) {
        return doctype != null
                && StringUtils.equals(NAME, doctype.getName())
                && StringUtils.equals(SYSTEM_ID, doctype.getSystemId())
                && StringUtils.equals(PUBLIC_ID, doctype.getPublicId());
    }
}
