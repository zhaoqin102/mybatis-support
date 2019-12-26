package org.muchu.mybatis.support.cofing;

import com.intellij.ide.FileIconProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.xml.XmlFile;
import com.intellij.util.xml.DomFileElement;
import com.intellij.util.xml.DomManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.muchu.mybatis.support.bean.Mapper;

import javax.swing.*;

public class MybatisIconProvider implements FileIconProvider {

    @Nullable
    @Override
    public Icon getIcon(@NotNull VirtualFile file, int flags, @Nullable Project project) {
        if (project == null) {
            return null;
        }
        PsiFile psiFile = PsiManager.getInstance(project).findFile(file);
        if (!(psiFile instanceof XmlFile)) {
            return null;
        }
        XmlFile xmlFile = (XmlFile) psiFile;
        DomFileElement<Mapper> element = DomManager.getDomManager(project).getFileElement(xmlFile, Mapper.class);
        if (element == null) {
            return null;
        }
        return IconLoader.getIcon("/images/mybatis.png", MybatisIconProvider.class);
    }
}
