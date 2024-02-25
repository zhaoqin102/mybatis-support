package org.muchu.mybatis.support.contributor;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlText;
import com.intellij.psi.xml.XmlToken;
import com.intellij.util.xml.DomFileElement;
import org.jetbrains.annotations.NotNull;
import org.muchu.mybatis.support.dom.model.Mapper;
import org.muchu.mybatis.support.service.MyDomService;

import java.util.List;

public class MybatisCompletionContributor extends CompletionContributor {

  @Override
  public void fillCompletionVariants(@NotNull CompletionParameters parameters, @NotNull CompletionResultSet result) {
    PsiElement psiElement = parameters.getPosition();
    if (!(psiElement instanceof XmlToken) || !(psiElement.getParent() instanceof XmlText)) {
      return;
    }
    PsiFile psiFile = parameters.getOriginalFile();
    if (!(psiFile instanceof XmlFile)) {
      return;
    }
    DomFileElement<Mapper> mapper = MyDomService.getInstance().getMapper((XmlFile) psiFile);
    if (mapper == null) {
      return;
    }
    if (!(psiElement.getParent() instanceof XmlText xmlText)) {
      return;
    }
    final String text = xmlText.getText();
    final int index = parameters.getOffset() - xmlText.getTextOffset() - 1;
    if (text.charAt(index) != '{') {
      return;
    }
    List<LookupElementBuilder> lookupElementBuilders = MyDomService.getInstance().getLookupElement(psiElement);
    result.addAllElements(lookupElementBuilders);
  }
}
