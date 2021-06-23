package org.muchu.mybatis.support.rename;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.refactoring.rename.RenamePsiElementProcessor;
import org.jetbrains.annotations.NotNull;
import org.muchu.mybatis.support.dom.model.Statement;
import org.muchu.mybatis.support.service.MyDomService;

import java.util.Map;

public class RenameIdProcessor extends RenamePsiElementProcessor {

  @Override
  public boolean canProcessElement(@NotNull PsiElement element) {
    return element instanceof PsiMethod;
  }

  @Override
  public void prepareRenaming(@NotNull PsiElement element, @NotNull String newName, @NotNull Map<PsiElement, String> allRenames) {
    Statement statement = MyDomService.getInstance().getStatement((PsiMethod) element, GlobalSearchScope.allScope(element.getProject()));
    if (statement != null) {
      allRenames.put(statement.getId().getXmlAttributeValue(), newName);
    }
  }
}
