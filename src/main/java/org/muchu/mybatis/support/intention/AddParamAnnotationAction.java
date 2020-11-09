package org.muchu.mybatis.support.intention;

import com.intellij.codeInsight.intention.impl.BaseIntentionAction;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

public class AddParamAnnotationAction extends BaseIntentionAction {

  @Nls(capitalization = Nls.Capitalization.Sentence)
  @NotNull
  @Override
  public String getText() {
    return "Add param annotation";
  }

  @Nls(capitalization = Nls.Capitalization.Sentence)
  @NotNull
  @Override
  public String getFamilyName() {
    return "Mybatis annotation";
  }

  @Override
  public boolean isAvailable(@NotNull Project project, Editor editor, PsiFile file) {
    if (!(file instanceof PsiJavaFile)) {
      return false;
    }
    int offset = editor.getCaretModel().getOffset();
    PsiElement element = file.findElementAt(offset);
    if (element == null || !(element.getParent() instanceof PsiParameter)) {
      return false;
    }
    PsiClass psiClass = PsiTreeUtil.getParentOfType(element, PsiClass.class);
    if (psiClass == null || !psiClass.isInterface()) {
      return false;
    }
    return true;
  }

  @Override
  public void invoke(@NotNull Project project, Editor editor, PsiFile file) throws IncorrectOperationException {
    ApplicationManager.getApplication().invokeLater(() -> WriteCommandAction.writeCommandAction(project).run(() -> {
      PsiElement element = file.findElementAt(editor.getCaretModel().getOffset());
      if (element == null || !(element.getParent() instanceof PsiParameter)) {
        return;
      }
      PsiParameter psiParameter = (PsiParameter) element.getParent();
      PsiModifierList modifierList = psiParameter.getModifierList();
      if (modifierList == null) {
        return;
      }
      modifierList.addAnnotation("org.apache.ibatis.annotations.Param(\"" + psiParameter.getName() + "\")");
      JavaCodeStyleManager instance = JavaCodeStyleManager.getInstance(project);
      instance.shortenClassReferences(file);
    }));
  }
}
