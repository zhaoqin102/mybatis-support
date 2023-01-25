package org.muchu.mybatis.support.intention;

import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiModifierList;
import com.intellij.psi.PsiParameter;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.ThrowableRunnable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

public class AddParamAnnotationAction extends PsiElementBaseIntentionAction {

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
  public boolean isAvailable(@NotNull Project project, Editor editor, @NotNull PsiElement element) {
    if (!(element.getParent() instanceof PsiParameter)) {
      return false;
    }
    PsiClass psiClass = PsiTreeUtil.getParentOfType(element, PsiClass.class);
    return psiClass != null && psiClass.isInterface();
  }

  @Override
  public void invoke(@NotNull Project project, Editor editor, @NotNull PsiElement element) throws IncorrectOperationException {
    if (!(element.getParent() instanceof PsiParameter psiParameter)) {
      return;
    }
    var task = new ThrowableRunnable<RuntimeException>() {

      @Override
      public void run() {
        PsiModifierList modifierList = psiParameter.getModifierList();
        if (modifierList == null) {
          return;
        }
        modifierList.addAnnotation("org.apache.ibatis.annotations.Param(\"" + psiParameter.getName() + "\")");
        JavaCodeStyleManager instance = JavaCodeStyleManager.getInstance(project);
        instance.shortenClassReferences(element.getContainingFile());
      }
    };
    if (!element.isPhysical()) {
      task.run();
      return;
    }
    ApplicationManager.getApplication().invokeLater(() -> WriteCommandAction.writeCommandAction(project).run(task));
  }
}
