package org.muchu.mybatis.support.search;

import com.intellij.openapi.application.QueryExecutorBase;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiElementProcessor;
import com.intellij.psi.search.searches.DefinitionsScopedSearch;
import com.intellij.psi.xml.*;
import com.intellij.util.Processor;
import com.intellij.util.xml.DomManager;
import com.intellij.xml.util.XmlUtil;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.muchu.mybatis.support.dom.model.Mapper;
import org.muchu.mybatis.support.service.MyDomService;

import java.util.List;

public class MapperDefinitionSearch extends QueryExecutorBase<PsiElement, PsiElement> {

  public MapperDefinitionSearch() {
    super(true);
  }

  @Override
  public void processQuery(@NotNull PsiElement queryParameters, @NotNull Processor<? super PsiElement> consumer) {
    if (!(queryParameters instanceof PsiClass)) {
      return;
    }
    PsiClass psiClass = (PsiClass) queryParameters;
    List<Mapper> mappers = MyDomService.getInstance().getMapper(psiClass,
        GlobalSearchScope.allScope(psiClass.getProject()));
    for (Mapper mapper : mappers) {
      XmlUtil.processXmlElements(mapper.getXmlTag(), element -> {
        if (element instanceof XmlAttributeValue
            && StringUtils.equals(psiClass.getQualifiedName(), ((XmlAttributeValue) element).getValue())) {
          return consumer.process(element);
        }
        return true;
      }, true);
    }
  }

}
