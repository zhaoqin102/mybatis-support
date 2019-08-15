package org.muchu.mybatis.support.completion;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.psi.PsiElement;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.ui.mac.MacPathChooserDialog;
import com.intellij.util.xml.DomFileElement;
import com.intellij.util.xml.DomService;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.muchu.mybatis.support.model.Mapper;
import org.muchu.mybatis.support.model.ResultMap;

import java.util.List;

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
        List<DomFileElement<Mapper>> fileElements = DomService.getInstance().getFileElements(Mapper.class, psiElement.getProject(),
                GlobalSearchScope.allScope(psiElement.getProject()));
        for (DomFileElement<Mapper> fileElement : fileElements) {
            List<ResultMap> resultMaps = fileElement.getRootElement().getResultMaps();
            for (ResultMap resultMap : resultMaps) {
                result.addElement(LookupElementBuilder.createWithSmartPointer(fileElement.getRootElement().getNamespace() + "." + resultMap.getId(), resultMap.getXmlTag()));
            }
        }
        super.fillCompletionVariants(parameters, result);
    }
}
