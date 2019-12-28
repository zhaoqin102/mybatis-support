package org.muchu.mybatis.support.config;

import com.intellij.ide.FileIconProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.muchu.mybatis.support.icon.MybatisIcon;
import org.muchu.mybatis.support.util.MybatisFileUtil;

import javax.swing.*;

public class MybatisIconProvider implements FileIconProvider {

    @Nullable
    @Override
    public Icon getIcon(@NotNull VirtualFile file, int flags, @Nullable Project project) {
        if (MybatisFileUtil.isMybatisFile(file, project)) {
            return MybatisIcon.MYBATIS_ICON;
        }
        return null;
    }
}
