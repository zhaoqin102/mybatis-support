package org.muchu.mybatis.support.completion;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlToken;
import com.intellij.util.ProcessingContext;
import com.intellij.util.xml.DomFileElement;
import com.intellij.util.xml.DomManager;
import com.intellij.util.xml.DomService;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.muchu.mybatis.support.bean.Mapper;
import org.muchu.mybatis.support.bean.ResultMap;

import java.util.List;

public class ResultMapCompletionContributor extends CompletionContributor {

    public ResultMapCompletionContributor() {
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(XmlToken.class),
                new CompletionProvider<CompletionParameters>() {
                    public void addCompletions(@NotNull CompletionParameters parameters,
                                               ProcessingContext context,
                                               @NotNull CompletionResultSet resultSet) {
                        PsiElement position = parameters.getPosition();
                        System.out.println(position.getClass());
                        if (!(parameters.getPosition().getParent() instanceof XmlAttributeValue)) {
                            return;
                        }
                        XmlAttributeValue xmlAttributeValue = (XmlAttributeValue) parameters.getPosition().getParent();
                        XmlAttribute xmlAttribute = (XmlAttribute) xmlAttributeValue.getParent();
                        if (!"resultMap".equals(xmlAttribute.getName())) {
                            return;
                        }
                        XmlFile xmlFile = (XmlFile) xmlAttribute.getContainingFile();
                        DomManager domManager = DomManager.getDomManager(xmlAttribute.getProject());
                        DomFileElement<Mapper> mapperDomFileElement = domManager.getFileElement(xmlFile, Mapper.class);
                        if (mapperDomFileElement == null) {
                            return;
                        }
                        List<DomFileElement<Mapper>> fileElements = DomService.getInstance().getFileElements(Mapper.class, parameters.getPosition().getProject(), GlobalSearchScope.allScope(parameters.getPosition().getProject()));
                        for (DomFileElement<Mapper> fileElement : fileElements) {
                            Mapper mapper = fileElement.getRootElement();
                            List<ResultMap> resultMaps = mapper.getResultMaps();
                            for (ResultMap resultMap : resultMaps) {
                                if (resultMap.getId() == null || resultMap.getId().getStringValue() == null) {
                                    continue;
                                }
                                if (StringUtils.equals(mapper.getNameSpace().getStringValue(), mapperDomFileElement.getRootElement().getNameSpace().getStringValue())) {
                                    resultSet.addElement(LookupElementBuilder.create(resultMap.getId().getStringValue()));
                                } else {
                                    resultSet.addElement(LookupElementBuilder.create(mapper.getNameSpace() + "." + resultMap.getId().getStringValue()));
                                }
                            }
                        }
                    }
                });
    }
}
