package org.muchu.mybatis.support.util;

import com.intellij.ide.highlighter.XmlFileType;
import com.intellij.internal.statistic.collectors.fus.fileTypes.FileTypeUsageSchemaDescriptor;
import com.intellij.openapi.fileTypes.FileTypeRegistry;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

public class MybatisFileTypeFactory implements FileTypeUsageSchemaDescriptor {

    @Override
    public boolean describes(@NotNull VirtualFile file) {
        return FileTypeRegistry.getInstance().isFileOfType(file, XmlFileType.INSTANCE);
    }
}
