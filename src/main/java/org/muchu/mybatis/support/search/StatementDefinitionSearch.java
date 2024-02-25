package org.muchu.mybatis.support.search;

import com.intellij.openapi.application.QueryExecutorBase;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.searches.DefinitionsScopedSearch;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.util.Processor;
import org.jetbrains.annotations.NotNull;
import org.muchu.mybatis.support.dom.model.Statement;
import org.muchu.mybatis.support.service.MyDomService;

public class StatementDefinitionSearch extends QueryExecutorBase<XmlAttributeValue, DefinitionsScopedSearch.SearchParameters> {

  public StatementDefinitionSearch() {
    super(true);
  }

  @Override
  public void processQuery(DefinitionsScopedSearch.@NotNull SearchParameters queryParameters, @NotNull Processor<? super XmlAttributeValue> consumer) {
    if (!(queryParameters.getElement() instanceof PsiMethod psiMethod)) {
      return;
    }
    Statement statement = MyDomService.getInstance().getStatement(psiMethod,
        GlobalSearchScope.allScope(psiMethod.getProject()));
    if (statement == null) {
      return;
    }
    consumer.process(statement.getId().getXmlAttributeValue());
  }

}
