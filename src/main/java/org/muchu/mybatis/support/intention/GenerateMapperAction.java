package org.muchu.mybatis.support.intention;

import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction;
import com.intellij.codeInsight.navigation.NavigationUtil;
import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.ide.fileTemplates.FileTemplateUtil;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiIdentifier;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.muchu.mybatis.support.template.MybatisFileTemplateGroupDescriptorFactory;

import java.util.Properties;

public class GenerateMapperAction extends PsiElementBaseIntentionAction {

  @NotNull
  @Override
  public String getText() {
    return "Create mapper.xml";
  }

  @Override
  public boolean isAvailable(@NotNull Project project, Editor editor, @NotNull PsiElement element) {
    if (!(element instanceof PsiIdentifier) || !(element.getParent() instanceof PsiClass)) {
      return false;
    }
    PsiClass psiClass = (PsiClass) element.getParent();
    return psiClass.isInterface();
  }

  @Nls
  @NotNull
  @Override
  public String getFamilyName() {
    return getText();
  }

  @Override
  public void invoke(@NotNull Project project, Editor editor, @NotNull PsiElement element) throws IncorrectOperationException {
    PsiClass psiClass = (PsiClass) element.getParent();
    final PsiDirectory sourceDir = psiClass.getContainingFile().getContainingDirectory();
    ProjectFileIndex fileIndex = ProjectRootManager.getInstance(project).getFileIndex();
    final PsiDirectory baseDir = sourceDir != null && fileIndex.getSourceRootForFile(sourceDir.getVirtualFile()) != null ? sourceDir : null;
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
    FileTemplateManager templateManager = FileTemplateManager.getInstance(project);
    FileTemplate mapperTemplate = templateManager.getJ2eeTemplate(MybatisFileTemplateGroupDescriptorFactory.MAPPER_XML);
    WriteCommandAction.writeCommandAction(project).run(() -> {
      try {
        PsiElement psiElement = FileTemplateUtil.createFromTemplate(mapperTemplate, dialog.getXmlName(), properties, dialog.getTargetDirectory());
        NavigationUtil.activateFileWithPsiElement(psiElement, true);
      } catch (Exception e) {
        throw new IncorrectOperationException("error creating validation.xml", (Throwable) e);
      }
    });
  }

}