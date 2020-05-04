package org.muchu.mybatis.support.service;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiType;
import com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.muchu.mybatis.support.bean.Mapper;
import org.muchu.mybatis.support.bean.Statement;
import sun.reflect.generics.tree.ReturnType;

public interface MyDomService {
    static MyDomService getInstance() {
        return ServiceManager.getService(MyDomService.class);
    }

    @Nullable
    Mapper getMapper(@NotNull PsiClass psiClass,
                     @Nullable GlobalSearchScope scope);

    @Nullable
    Statement getStatement(@NotNull PsiMethod psiMethod,
                           @Nullable GlobalSearchScope scope);
}