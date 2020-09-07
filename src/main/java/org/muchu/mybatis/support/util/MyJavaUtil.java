package org.muchu.mybatis.support.util;

import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.search.GlobalSearchScope;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.muchu.mybatis.support.dom.Mapper;

public class MyJavaUtil {

    public static PsiClass findClass(Mapper mapper, @NotNull Project project) {
        if (mapper == null || mapper.getNameSpace() == null || StringUtils.isBlank(mapper.getNameSpace().getValue())) {
            return null;
        }
        JavaPsiFacade javaPsiFacade = JavaPsiFacade.getInstance(project);
        return javaPsiFacade.findClass(mapper.getNameSpace().getValue(), GlobalSearchScope.allScope(project));
    }
}