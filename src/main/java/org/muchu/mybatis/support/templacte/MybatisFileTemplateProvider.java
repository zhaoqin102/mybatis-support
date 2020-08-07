package org.muchu.mybatis.support.templacte;

import com.intellij.ide.fileTemplates.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.io.FileUtilRt;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.muchu.mybatis.support.icon.MybatisIcon;

import java.util.Properties;

public class MybatisFileTemplateProvider implements FileTemplateGroupDescriptorFactory {

    @Override
    public FileTemplateGroupDescriptor getFileTemplatesDescriptor() {
        FileTemplateGroupDescriptor group = new FileTemplateGroupDescriptor("Mybatis", MybatisIcon.MYBATIS_ICON);
        group.addTemplate(new FileTemplateDescriptor(MybatisTemplate.MYBATIS_MAPPER_XML_TEMPLATE, MybatisIcon.MYBATIS_ICON));
        return group;
    }


    @Nullable
    public static PsiElement createFromTemplate(@NotNull Project project,
                                                @NotNull VirtualFile rootDir,
                                                @NotNull String templateName,
                                                @NotNull String fileName,
                                                @NotNull Properties properties) throws Exception {
        rootDir.refresh(false, false);
        PsiDirectory directory = PsiManager.getInstance(project).findDirectory(rootDir);
        if (directory != null) {
            return createFromTemplate(templateName, fileName, directory, properties);
        }
        return null;
    }


    @Nullable
    public static PsiElement createFromTemplate(@NotNull Project project,
                                                @NotNull VirtualFile rootDir,
                                                @NotNull String templateName,
                                                @NotNull String fileName) throws Exception {
        return createFromTemplate(project, rootDir, templateName, fileName, FileTemplateManager.getInstance(project).getDefaultProperties());
    }

    public static PsiElement createFromTemplate(String templateName, String fileName, @NotNull PsiDirectory directory, Properties properties)
            throws Exception {
        FileTemplateManager manager = FileTemplateManager.getInstance(directory.getProject());
        FileTemplate template = manager.getJ2eeTemplate(templateName);
        return FileTemplateUtil.createFromTemplate(template, fileName, properties, directory);
    }

    public static PsiElement createFromTemplate(String templateName, String fileName, @NotNull PsiDirectory directory) throws Exception {
        return createFromTemplate(templateName, fileName, directory, FileTemplateManager.getInstance(directory.getProject()).getDefaultProperties());
    }

    @NotNull
    public static String getFileNameByNewElementName(@NotNull String name) {
        if (!FileUtilRt.extensionEquals(name, "xml")) {
            name += ".xml";
        }
        return name;
    }
}
