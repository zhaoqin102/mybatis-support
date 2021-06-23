package org.muchu.mybatis.support.editorActions;

import com.intellij.codeInsight.AutoPopupController;
import com.intellij.codeInsight.editorActions.TypedHandlerDelegate;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlText;
import org.jetbrains.annotations.NotNull;
import org.muchu.mybatis.support.service.MyDomService;

public class StatementParameterTypedHandler extends TypedHandlerDelegate {

  @Override
  public @NotNull Result charTyped(char c, @NotNull Project project, @NotNull Editor editor, @NotNull PsiFile file) {
    if (!(file instanceof XmlFile)) return Result.CONTINUE;
    XmlFile xmlFile = (XmlFile) file;
    if (MyDomService.getInstance().getMapper(xmlFile) == null) {
      return Result.CONTINUE;
    }
    final PsiElement at = file.findElementAt(editor.getCaretModel().getOffset());
    if (at == null || !(at.getParent() instanceof XmlText)) {
      return Result.CONTINUE;
    }
    if (c == '{') {
      AutoPopupController.getInstance(project).autoPopupMemberLookup(editor, null);
      return Result.STOP;
    }
    return Result.CONTINUE;
  }

}
