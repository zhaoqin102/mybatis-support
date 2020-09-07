package org.muchu.mybatis.support.completion;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
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
import org.muchu.mybatis.support.dom.MyBatisRoot;
import org.muchu.mybatis.support.dom.model.ResultMap;

import java.util.List;

public class ResultMapCompletionContributor extends CompletionContributor {

    public ResultMapCompletionContributor() {
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(XmlToken.class),
                new CompletionProvider<CompletionParameters>() {
                    public void addCompletions(@NotNull CompletionParameters parameters,
                                               ProcessingContext context,
                                               @NotNull CompletionResultSet resultSet) {
                        if (!(parameters.getPosition().getParent() instanceof XmlAttributeValue)) {
                            return;
                        }
                        XmlAttribute xmlAttribute = PsiTreeUtil.getParentOfType(parameters.getPosition(), XmlAttribute.class);
                        if (xmlAttribute == null || !"resultMap".equals(xmlAttribute.getName())) {
                            return;
                        }
                        XmlFile xmlFile = PsiTreeUtil.getParentOfType(parameters.getPosition(), XmlFile.class);
                        if (xmlFile == null) {
                            return;
                        }
                        DomManager domManager = DomManager.getDomManager(xmlAttribute.getProject());
                        DomFileElement<MyBatisRoot> mapperDomFileElement = domManager.getFileElement(xmlFile, MyBatisRoot.class);
                        if (mapperDomFileElement == null) {
                            return;
                        }
                        List<DomFileElement<MyBatisRoot>> fileElements = DomService.getInstance().getFileElements(MyBatisRoot.class, parameters.getPosition().getProject(), GlobalSearchScope.allScope(parameters.getPosition().getProject()));
                        for (DomFileElement<MyBatisRoot> fileElement : fileElements) {
                            MyBatisRoot myBatisRoot = fileElement.getRootElement();
                            List<ResultMap> resultMaps = myBatisRoot.getResultMaps();
                            for (ResultMap resultMap : resultMaps) {
                                if (resultMap.getId() == null || resultMap.getId().getStringValue() == null) {
                                    continue;
                                }
                                if (StringUtils.equals(myBatisRoot.getNameSpace().getStringValue(), mapperDomFileElement.getRootElement().getNameSpace().getStringValue())) {
                                    resultSet.addElement(LookupElementBuilder.create(resultMap.getId().getStringValue()));
                                } else {
                                    resultSet.addElement(LookupElementBuilder.create(myBatisRoot.getNameSpace() + "." + resultMap.getId().getStringValue()));
                                }
                            }
                        }
                    }
                });
    }
}
