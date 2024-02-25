package org.muchu.mybatis.support.util;

import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.search.GlobalSearchScope;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.muchu.mybatis.support.dom.model.Mapper;

public class MyJavaUtil {

  public static PsiClass findClass(Mapper mapper, @NotNull Project project) {
    if (mapper == null || StringUtils.isBlank(mapper.getNamespace().getValue())) {
      return null;
    }
    JavaPsiFacade javaPsiFacade = JavaPsiFacade.getInstance(project);
    return javaPsiFacade.findClass(mapper.getNamespace().getValue(), GlobalSearchScope.allScope(project));
  }
}