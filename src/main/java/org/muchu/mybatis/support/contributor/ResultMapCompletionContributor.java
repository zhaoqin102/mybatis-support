package org.muchu.mybatis.support.contributor;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.psi.xml.XmlFile;
import com.intellij.util.xml.DomFileElement;
import com.intellij.util.xml.DomManager;
import com.intellij.util.xml.DomService;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.muchu.mybatis.support.dom.model.Mapper;
import org.muchu.mybatis.support.dom.model.ResultMap;

import java.util.List;
import java.util.Objects;

public class ResultMapCompletionContributor extends CompletionContributor {

  @Override
  public void fillCompletionVariants(@NotNull CompletionParameters parameters, @NotNull CompletionResultSet result) {
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
    List<DomFileElement<Mapper>> fileElements = DomService.getInstance()
        .getFileElements(Mapper.class, parameters.getPosition().getProject(),
            GlobalSearchScope.allScope(parameters.getPosition().getProject()));
    for (DomFileElement<Mapper> fileElement : fileElements) {
      Mapper mapper = fileElement.getRootElement();
      List<ResultMap> resultMaps = mapper.getResultMaps();
      for (ResultMap resultMap : resultMaps) {
        resultMap.getId();
        if (resultMap.getId().getStringValue() == null) {
          continue;
        }
        if (Objects.equals(mapper.getNamespace().getStringValue(), mapperDomFileElement.getRootElement().getNamespace().getStringValue())) {
          result.addElement(LookupElementBuilder.create(resultMap.getId().getStringValue()));
        } else {
          result.addElement(LookupElementBuilder.create(mapper.getNamespace() + "." + resultMap.getId().getStringValue()));
        }
      }
    }
  }
}
