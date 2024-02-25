package org.muchu.mybatis.support.search;

import com.intellij.openapi.application.QueryExecutorBase;
import com.intellij.psi.PsiClass;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.searches.DefinitionsScopedSearch;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.util.Processor;
import org.jetbrains.annotations.NotNull;
import org.muchu.mybatis.support.dom.model.Mapper;
import org.muchu.mybatis.support.service.MyDomService;

import java.util.List;

public class MapperDefinitionSearch extends QueryExecutorBase<XmlAttributeValue, DefinitionsScopedSearch.SearchParameters> {

  public MapperDefinitionSearch() {
    super(true);
  }

  @Override
  public void processQuery(DefinitionsScopedSearch.@NotNull SearchParameters queryParameters, @NotNull Processor<? super XmlAttributeValue> consumer) {
    if (!(queryParameters.getElement() instanceof PsiClass psiClass)) {
      return;
    }
    List<Mapper> mappers = MyDomService.getInstance().getMapper(psiClass,
        GlobalSearchScope.allScope(psiClass.getProject()));
    mappers.forEach(mapper -> consumer.process(mapper.getNamespace().getXmlAttributeValue()));
  }

}
