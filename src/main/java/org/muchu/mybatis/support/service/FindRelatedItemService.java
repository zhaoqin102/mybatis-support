package org.muchu.mybatis.support.service;

import com.intellij.psi.PsiElement;

import java.util.List;

public interface FindRelatedItemService {

    boolean isSupport(PsiElement psiElement);

    List<PsiElement> findRelatedItem(PsiElement psiElement);
}
