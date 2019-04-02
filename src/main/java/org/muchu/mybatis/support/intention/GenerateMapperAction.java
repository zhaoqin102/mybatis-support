package org.muchu.mybatis.support.intention;

import com.intellij.bootRuntime.Model;
import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.ide.fileTemplates.FileTemplateUtil;
import com.intellij.ide.util.PackageUtil;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.psi.*;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.muchu.mybatis.support.constant.MybatisTemplate;
import org.muchu.mybatis.support.intention.impl.CreateXmlDialog;

import java.util.Properties;

/**
 *
 */
public class GenerateMapperAction implements IntentionAction {


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
            PsiJavaFile javaFile = (PsiJavaFile) file;
            PsiClass[] classes = javaFile.getClasses();
            return classes.length >= 1 && classes[0].isInterface();
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
        final PsiPackage aPackage = sourceDir != null ? JavaDirectoryService.getInstance().getPackage(sourceDir) : null;
        PsiClass[] classes = psiJavaFile.getClasses();
        String targetXmlName = null;
        if (classes.length >= 1) {
            targetXmlName = classes[0].getName();
        }
        targetXmlName = targetXmlName == null ? "" : targetXmlName;
        final CreateXmlDialog dialog = new CreateXmlDialog(
                project, "create Mybatis XML file",
                targetXmlName,
                aPackage != null ? aPackage.getQualifiedName() : "",
                true, ModuleUtilCore.findModuleForPsiElement(psiJavaFile)) {
            @Override
            protected PsiDirectory getBaseDir(String packageName) {
                return sourceDir != null && fileIndex.getSourceRootForFile(sourceDir.getVirtualFile()) != null ? sourceDir : super.getBaseDir(packageName);
            }

            @Override
            protected boolean reportBaseInTestSelectionInSource() {
                return true;
            }
        };
        if (!dialog.showAndGet()) {
            return;
        }
        Properties properties = new Properties();
        properties.setProperty("namespace", classes[0].getQualifiedName());
        FileTemplate fileTemplate = FileTemplateManager.getInstance(project).getJ2eeTemplate(MybatisTemplate.MYBATIS_MAPPER_XML_TEMPLATE);
        try {
            PsiElement psiElement = FileTemplateUtil.createFromTemplate(fileTemplate, dialog.getClassName() + ".xml", properties, dialog.getTargetDirectory());
            Module module = null;
            PackageUtil.findOrCreateDirectoryForPackage(module, "", dialog.getTargetDirectory(), false).add(psiElement);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}