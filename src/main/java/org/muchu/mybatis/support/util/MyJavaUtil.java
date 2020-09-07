package org.muchu.mybatis.support.util;

import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.search.GlobalSearchScope;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.muchu.mybatis.support.dom.MyBatisRoot;

public class MyJavaUtil {

    public static PsiClass findClass(MyBatisRoot myBatisRoot, @NotNull Project project) {
        if (myBatisRoot == null || myBatisRoot.getNameSpace() == null || StringUtils.isBlank(myBatisRoot.getNameSpace().getValue())) {
            return null;
        }
        JavaPsiFacade javaPsiFacade = JavaPsiFacade.getInstance(project);
        return javaPsiFacade.findClass(myBatisRoot.getNameSpace().getValue(), GlobalSearchScope.allScope(project));
    }
}