package org.muchu.mybatis.support.language;

import com.intellij.lang.xml.XMLLanguage;

public class MybatisLanguage extends XMLLanguage {

    public final static MybatisLanguage INSTANCE = new MybatisLanguage();

    private MybatisLanguage() {
        super(XMLLanguage.INSTANCE, "Mybatis", "application/xml", "text/xml");
    }

}
