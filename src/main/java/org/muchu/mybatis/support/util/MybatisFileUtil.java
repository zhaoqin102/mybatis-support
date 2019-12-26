package org.muchu.mybatis.support.util;

import com.intellij.ide.highlighter.XmlFileType;
import com.intellij.openapi.fileTypes.FileTypeRegistry;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.xml.XmlFile;
import com.intellij.util.xml.DomFileElement;
import com.intellij.util.xml.DomManager;
import org.muchu.mybatis.support.bean.Mapper;

public class MybatisFileUtil {

    public static boolean isMybatisFile(VirtualFile file, Project project) {
        if (project == null) {
            return false;
        }
        PsiFile psiFile = PsiManager.getInstance(project).findFile(file);
        if (!(psiFile instanceof XmlFile)) {
            return false;
        }
        DomFileElement<Mapper> element = DomManager.getDomManager(project).getFileElement((XmlFile) psiFile, Mapper.class);
        return element != null;
    }
}
