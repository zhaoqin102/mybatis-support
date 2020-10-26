package org.muchu.mybatis.support.template;

import com.intellij.ide.fileTemplates.FileTemplateDescriptor;
import com.intellij.ide.fileTemplates.FileTemplateGroupDescriptor;
import com.intellij.ide.fileTemplates.FileTemplateGroupDescriptorFactory;
import org.muchu.mybatis.support.MyBatisBundle;
import org.muchu.mybatis.support.icon.MybatisIcon;

public class MybatisFileTemplateGroupDescriptorFactory implements FileTemplateGroupDescriptorFactory {
    public static final String MAPPER_XML = "MybatisMapper.xml";

    @Override
    public FileTemplateGroupDescriptor getFileTemplatesDescriptor() {
        final FileTemplateGroupDescriptor group = new FileTemplateGroupDescriptor(MyBatisBundle.message("mybatis"),
                MybatisIcon.MYBATIS_ICON);
        group.addTemplate(new FileTemplateDescriptor(MAPPER_XML, MybatisIcon.MYBATIS_ICON));
        return group;
    }

}
