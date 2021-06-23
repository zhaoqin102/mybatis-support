package org.muchu.mybatis.support.service;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.xml.XmlFile;
import com.intellij.util.xml.DomFileElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.muchu.mybatis.support.dom.model.Mapper;
import org.muchu.mybatis.support.dom.model.Statement;

import java.util.List;

public interface MyDomService {
  static MyDomService getInstance() {
    return ServiceManager.getService(MyDomService.class);
  }

  @NotNull
  List<Mapper> getMapper(@NotNull PsiClass psiClass,
                         @Nullable GlobalSearchScope scope);

  @Nullable
  Statement getStatement(@NotNull PsiMethod psiMethod,
                         @Nullable GlobalSearchScope scope);

  @Nullable DomFileElement<Mapper> getMapper(XmlFile xmlFile);
}
