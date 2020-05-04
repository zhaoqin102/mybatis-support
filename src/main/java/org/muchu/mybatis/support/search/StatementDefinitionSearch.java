package org.muchu.mybatis.support.search;

import com.intellij.openapi.application.QueryExecutorBase;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.xml.XmlElement;
import com.intellij.util.Processor;
import org.jetbrains.annotations.NotNull;
import org.muchu.mybatis.support.bean.Statement;
import org.muchu.mybatis.support.service.MyDomService;

public class StatementDefinitionSearch extends QueryExecutorBase<XmlElement, PsiElement> {

    public StatementDefinitionSearch() {
        super(true);
    }

    @Override
    public void processQuery(@NotNull PsiElement psiElement, @NotNull Processor<? super XmlElement> consumer) {
        if (!(psiElement instanceof PsiMethod)) {
            return;
        }
        PsiMethod psiMethod = (PsiMethod) psiElement;
        Statement statement = MyDomService.getInstance().getStatement(psiMethod,
                GlobalSearchScope.allScope(psiMethod.getProject()));
        if (statement != null) {
            consumer.process(statement.getXmlElement());
        }
    }
}
