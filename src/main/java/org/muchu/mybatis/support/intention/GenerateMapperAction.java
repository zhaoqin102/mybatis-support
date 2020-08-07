package org.muchu.mybatis.support.intention;

import com.intellij.CommonBundle;
import com.intellij.codeInsight.intention.impl.BaseIntentionAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.*;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.xml.DomManager;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.muchu.mybatis.support.templacte.MybatisFileTemplateProvider;
import org.muchu.mybatis.support.templacte.MybatisTemplate;

import java.util.Properties;

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
        if (!(file instanceof PsiJavaFile)) {
            return false;
        }
        int offset = editor.getCaretModel().getOffset();
        PsiElement element = file.findElementAt(offset);
        if (element == null) {
            return false;
        }
        if (!(element instanceof PsiIdentifier) || !(element.getParent() instanceof PsiClass)) {
            return false;
        }
        PsiClass psiClass = (PsiClass) element.getParent();
        if (!psiClass.isInterface()) {
            return false;
        }
        return true;
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
        int offset = editor.getCaretModel().getOffset();
        PsiElement element = file.findElementAt(offset);
        if (element == null) {
            return;
        }
        if (!(element instanceof PsiIdentifier) || !(element.getParent() instanceof PsiClass)) {
            return;
        }
        PsiClass psiClass = (PsiClass) element.getParent();
        if (!psiClass.isInterface()) {
            return;
        }
        String targetXmlName = psiClass.getName();
        targetXmlName = targetXmlName == null ? "" : targetXmlName;
        final CreateXmlDialog dialog = new CreateXmlDialog(
                project, "Create MyBatis xml file",
                targetXmlName,
                baseDir);
        if (!dialog.showAndGet()) {
            return;
        }
        Properties properties = new Properties();
        properties.setProperty("namespace", psiClass.getQualifiedName());
        try {
            MybatisFileTemplateProvider.createFromTemplate(MybatisTemplate.MYBATIS_MAPPER_XML_TEMPLATE, MybatisFileTemplateProvider.getFileNameByNewElementName(dialog.getXmlName()), dialog.getTargetDirectory(), properties);
        } catch (Exception e) {
            Messages.showMessageDialog(project, e.getMessage(), CommonBundle.getErrorTitle(), Messages.getErrorIcon());
        }
    }

}