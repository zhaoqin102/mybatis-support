package org.muchu.mybatis.support.service.impl;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.xml.DomFileElement;
import com.intellij.util.xml.DomService;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.muchu.mybatis.support.dom.model.Mapper;
import org.muchu.mybatis.support.dom.model.Statement;
import org.muchu.mybatis.support.service.MyDomService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MyDomServiceImpl implements MyDomService {

  @Override
  public @NotNull List<Mapper> getMapper(@NotNull PsiClass psiClass, @Nullable GlobalSearchScope scope) {
    if (!psiClass.isInterface()) {
      return new ArrayList<>();
    }
    Project project = psiClass.getProject();
    List<DomFileElement<Mapper>> mappers = DomService.getInstance().getFileElements(Mapper.class, project, scope);
    return mappers.stream()
        .map(DomFileElement::getRootElement)
        .filter(mapper ->
            StringUtils.equals(psiClass.getQualifiedName(),
                mapper.getNamespace().getValue()))
        .collect(Collectors.toList());
  }

  @Nullable
  @Override
  public Statement getStatement(@NotNull PsiMethod psiMethod, @Nullable GlobalSearchScope scope) {
    PsiClass psiClass = psiMethod.getContainingClass();
    if (psiClass == null) {
      return null;
    }
    List<Mapper> mappers = getMapper(psiClass, scope);
    //TODO check return type
    return mappers.stream().map(Mapper::getStatements).flatMap(Collection::stream)
        .filter(statement -> StringUtils.equals(psiMethod.getName(), statement.getId().getValue()))
        .findFirst().orElse(null);
  }
}
