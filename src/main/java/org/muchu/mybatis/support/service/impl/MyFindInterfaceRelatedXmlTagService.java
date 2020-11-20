package org.muchu.mybatis.support.service.impl;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiIdentifier;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.xml.GenericAttributeValue;
import org.muchu.mybatis.support.dom.model.Mapper;
import org.muchu.mybatis.support.service.MyDomService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyFindInterfaceRelatedXmlTagService extends AbstractFindRelatedItemService {
  public static MyFindInterfaceRelatedXmlTagService INSTANCE = new MyFindInterfaceRelatedXmlTagService();

  private MyFindInterfaceRelatedXmlTagService() {
  }

  @Override
  public boolean isSupport(PsiElement psiElement) {
    PsiElement parent = psiElement.getParent();
    if (!(psiElement instanceof PsiIdentifier) || !(parent instanceof PsiClass)) {
      return false;
    }
    PsiClass psiClass = (PsiClass) parent;
    return psiClass.isInterface();
  }

  @Override
  public List<PsiElement> findRelatedItem(PsiElement psiElement) {
    List<PsiElement> resultList = new ArrayList<>();
    PsiClass psiClass = (PsiClass) psiElement.getParent();
    List<Mapper> mappers = MyDomService.getInstance().getMapper(psiClass, GlobalSearchScope.allScope(psiElement.getProject()));
    mappers.forEach(mapper -> {
      resultList.add(mapper.getNamespace().getXmlElement());
    });
    return resultList;
  }
}
