package org.muchu.mybatis.support.service.impl;

import com.intellij.psi.PsiElement;

import java.util.ArrayList;
import java.util.List;

public class MyNotSupportElementService extends AbstractFindRelatedItemService {
    public static MyNotSupportElementService INSTANCE = new MyNotSupportElementService();

    private MyNotSupportElementService() {
    }

    @Override
    public boolean isSupport(PsiElement psiElement) {
        return true;
    }

    @Override
    public List<PsiElement> findRelatedItem(PsiElement psiElement) {
        return new ArrayList<>();
    }
}
