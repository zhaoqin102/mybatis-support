package org.muchu.mybatis.support.service.impl;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiIdentifier;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.search.GlobalSearchScope;
import org.muchu.mybatis.support.bean.Statement;
import org.muchu.mybatis.support.service.MyDomService;

import java.util.ArrayList;
import java.util.List;

public class MyFindMethodRelatedXmlTagService extends AbstractFindRelatedItemService {
    public static MyFindMethodRelatedXmlTagService INSTANCE = new MyFindMethodRelatedXmlTagService();

    private MyFindMethodRelatedXmlTagService() {
    }

    @Override
    public boolean isSupport(PsiElement psiElement) {
        PsiElement parent = psiElement.getParent();
        if (!(psiElement instanceof PsiIdentifier) || !(parent instanceof PsiMethod)) {
            return false;
        }
        PsiMethod psiMethod = (PsiMethod) parent;
        if (psiMethod.isConstructor()) {
            return false;
        }
        return true;
    }

    @Override
    public List<PsiElement> findRelatedItem(PsiElement psiElement) {
        List<PsiElement> resultList = new ArrayList<>();
        PsiMethod psiMethod = (PsiMethod) psiElement.getParent();
        Statement statement = MyDomService.getInstance().getStatement(psiMethod, GlobalSearchScope.allScope(psiElement.getProject()));
        if (statement != null) {
            resultList.add(statement.getXmlElement());
        }
        return resultList;
    }

}
