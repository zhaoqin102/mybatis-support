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
import org.muchu.mybatis.support.dom.model.Mapper;
import org.muchu.mybatis.support.dom.model.ResultMap;

import java.util.List;

public class ResultMapCompletionContributor extends CompletionContributor {

  public ResultMapCompletionContributor() {
    extend(CompletionType.BASIC,
        PlatformPatterns.psiElement(XmlToken.class),
        new CompletionProvider<>() {
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
            DomFileElement<Mapper> mapperDomFileElement = domManager.getFileElement(xmlFile, Mapper.class);
            if (mapperDomFileElement == null) {
              return;
            }
            List<DomFileElement<Mapper>> fileElements = DomService.getInstance().getFileElements(Mapper.class, parameters.getPosition().getProject(), GlobalSearchScope.allScope(parameters.getPosition().getProject()));
            for (DomFileElement<Mapper> fileElement : fileElements) {
              Mapper mapper = fileElement.getRootElement();
              List<ResultMap> resultMaps = mapper.getResultMaps();
              for (ResultMap resultMap : resultMaps) {
                resultMap.getId();
                if (resultMap.getId().getStringValue() == null) {
                  continue;
                }
                if (StringUtils.equals(mapper.getNamespace().getStringValue(), mapperDomFileElement.getRootElement().getNamespace().getStringValue())) {
                  resultSet.addElement(LookupElementBuilder.create(resultMap.getId().getStringValue()));
                } else {
                  resultSet.addElement(LookupElementBuilder.create(mapper.getNamespace() + "." + resultMap.getId().getStringValue()));
                }
              }
            }
          }
        });
  }
}
