package org.muchu.mybatis.support.config;

import com.intellij.ide.FileIconProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.xml.XmlFile;
import com.intellij.ui.IconManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.muchu.mybatis.support.util.MybatisFileUtil;

import javax.swing.*;

public class MybatisIconProvider implements FileIconProvider {

    @Nullable
    @Override
    public Icon getIcon(@NotNull VirtualFile file, int flags, @Nullable Project project) {
        if (MybatisFileUtil.isMybatisFile(file, project)) {
            return IconManager.getInstance().getIcon("/images/mybatis.png", MybatisIconProvider.class);
        }
        return null;
    }
}
