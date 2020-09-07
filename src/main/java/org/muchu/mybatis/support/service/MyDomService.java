package org.muchu.mybatis.support.service;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.muchu.mybatis.support.dom.MyBatisRoot;
import org.muchu.mybatis.support.dom.model.Statement;

public interface MyDomService {
    static MyDomService getInstance() {
        return ServiceManager.getService(MyDomService.class);
    }

    @Nullable
    MyBatisRoot getMapper(@NotNull PsiClass psiClass,
                          @Nullable GlobalSearchScope scope);

    @Nullable
    Statement getStatement(@NotNull PsiMethod psiMethod,
                           @Nullable GlobalSearchScope scope);
}