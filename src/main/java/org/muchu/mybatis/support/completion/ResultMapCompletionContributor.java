package org.muchu.mybatis.support.completion;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.psi.PsiElement;
import com.intellij.psi.xml.XmlAttribute;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

public class ResultMapCompletionContributor extends CompletionContributor {

    private String myAttributeName = "resultMap";

    @Override
    public void fillCompletionVariants(@NotNull CompletionParameters parameters, @NotNull CompletionResultSet result) {
        if (parameters.getCompletionType() != CompletionType.BASIC) return;
        PsiElement psiElement = parameters.getPosition();
        if (psiElement.getParent() == null) return;
        PsiElement parent = psiElement.getParent().getParent();
        if (!(parent instanceof XmlAttribute)) return;
        XmlAttribute xmlAttribute = (XmlAttribute) parent;
        if (!StringUtils.equals(xmlAttribute.getName(), myAttributeName)) return;
        result.restartCompletionWhenNothingMatches();
        result.addElement(LookupElementBuilder.create("hello world"));
        super.fillCompletionVariants(parameters, result);
    }
}
