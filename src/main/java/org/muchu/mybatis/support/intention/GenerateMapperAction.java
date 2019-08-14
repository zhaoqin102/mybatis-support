package org.muchu.mybatis.support.intention;

import com.intellij.CommonBundle;
import com.intellij.codeInsight.intention.impl.BaseIntentionAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.*;
import com.intellij.psi.search.searches.ClassInheritorsSearch;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.Query;
import com.intellij.util.xml.DomElement;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.muchu.mybatis.support.constant.MybatisTemplate;
import org.muchu.mybatis.support.fileTemplates.MybatisFileTemplateProvider;
import org.muchu.mybatis.support.util.MyJavaUtil;
import org.muchu.mybatis.support.util.MyXmlUtil;

import java.util.Properties;

/**
 *
 */
public class GenerateMapperAction extends BaseIntentionAction {


    private String name = "Create mapper";

    @Nls
    @NotNull
    @Override
    public String getFamilyName() {
        return name;
    }

    @Override
    public boolean isAvailable(@NotNull Project project, Editor editor, PsiFile file) {
        if (file instanceof PsiJavaFile) {
            PsiElement element = MyJavaUtil.getPsiElement(editor, file);
            PsiClass psiClass = PsiTreeUtil.getParentOfType(element, PsiClass.class);
            if (psiClass == null || !psiClass.isInterface()) {
                return false;
            }
            PsiElement lBrace = psiClass.getLBrace();
            if (lBrace == null) {
                return false;
            } else if (element.getTextOffset() >= lBrace.getTextOffset()) {
                return false;
            } else {
                Query<PsiClass> search = ClassInheritorsSearch.search(psiClass);
                if (search.findAll().size() > 0) {
                    return false;
                }
                DomElement domElement = MyXmlUtil.process(psiClass);
                return domElement == null;
            }
        }
        return false;
    }

    public GenerateMapperAction() {
    }

    @NotNull
    @Override
    public String getText() {
        return name;
    }

    @Override
    public boolean startInWriteAction() {
        return false;
    }

    @Override
    public void invoke(@NotNull final Project project, final Editor editor, PsiFile file) throws IncorrectOperationException {
        PsiJavaFile psiJavaFile = (PsiJavaFile) file;
        final PsiDirectory sourceDir = psiJavaFile.getContainingFile().getContainingDirectory();
        ProjectFileIndex fileIndex = ProjectRootManager.getInstance(project).getFileIndex();
        final PsiDirectory baseDir = sourceDir != null && fileIndex.getSourceRootForFile(sourceDir.getVirtualFile()) != null ? sourceDir : null;
        PsiClass[] classes = psiJavaFile.getClasses();
        String targetXmlName = null;
        if (classes.length >= 1) {
            targetXmlName = classes[0].getName();
        }
        targetXmlName = targetXmlName == null ? "" : targetXmlName;
        final CreateXmlDialog dialog = new CreateXmlDialog(
                project, "Create MyBatis xml file",
                targetXmlName,
                baseDir);
        if (!dialog.showAndGet()) {
            return;
        }
        Properties properties = new Properties();
        properties.setProperty("namespace", classes[0].getQualifiedName());
        try {
            MybatisFileTemplateProvider.createFromTemplate(MybatisTemplate.MYBATIS_MAPPER_XML_TEMPLATE, MybatisFileTemplateProvider.getFileNameByNewElementName(dialog.getXmlName()), dialog.getTargetDirectory(), properties);
        } catch (Exception e) {
            Messages.showMessageDialog(project, e.getMessage(), CommonBundle.getErrorTitle(), Messages.getErrorIcon());
        }
    }

}