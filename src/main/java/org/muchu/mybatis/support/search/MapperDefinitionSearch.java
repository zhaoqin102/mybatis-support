package org.muchu.mybatis.support.search;

import com.intellij.openapi.application.QueryExecutorBase;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.xml.XmlElement;
import com.intellij.util.Processor;
import org.jetbrains.annotations.NotNull;
import org.muchu.mybatis.support.dom.MyBatisRoot;
import org.muchu.mybatis.support.service.MyDomService;

public class MapperDefinitionSearch extends QueryExecutorBase<XmlElement, PsiElement> {

    public MapperDefinitionSearch() {
        super(true);
    }

    @Override
    public void processQuery(@NotNull PsiElement psiElement, @NotNull Processor<? super XmlElement> consumer) {
        if (!(psiElement instanceof PsiClass)) {
            return;
        }
        PsiClass psiClass = (PsiClass) psiElement;
        MyBatisRoot myBatisRoot = MyDomService.getInstance().getMapper(psiClass, GlobalSearchScope.allScope(psiClass.getProject()));
        if (myBatisRoot != null) {
            consumer.process(myBatisRoot.getXmlElement());
        }
    }

}
