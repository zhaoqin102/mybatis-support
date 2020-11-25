package org.muchu.mybatis.support.search;

import com.intellij.openapi.application.QueryExecutorBase;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.util.Processor;
import com.intellij.xml.util.XmlUtil;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.muchu.mybatis.support.dom.model.Statement;
import org.muchu.mybatis.support.service.MyDomService;

public class StatementDefinitionSearch extends QueryExecutorBase<PsiElement, PsiElement> {

  public StatementDefinitionSearch() {
    super(true);
  }

  @Override
  public void processQuery(@NotNull PsiElement queryParameters, @NotNull Processor<? super PsiElement> consumer) {
    if (!(queryParameters instanceof PsiMethod)) {
      return;
    }
    PsiMethod psiMethod = (PsiMethod) queryParameters;
    Statement statement = MyDomService.getInstance().getStatement(psiMethod,
        GlobalSearchScope.allScope(psiMethod.getProject()));
    if (statement != null) {
      XmlUtil.processXmlElements(statement.getXmlTag(), element -> {
        if (element instanceof XmlAttributeValue
            && StringUtils.equals(psiMethod.getName(), ((XmlAttributeValue) element).getValue())) {
          return consumer.process(element);
        }
        return true;
      }, true);
    }
  }
}
