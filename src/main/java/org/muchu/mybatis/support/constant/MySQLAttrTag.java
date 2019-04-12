package org.muchu.mybatis.support.constant;

import com.intellij.psi.PsiMethod;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public enum MySQLAttrTag {

    ID("id", "getName");

    private String value;

    private String methodName;

    MySQLAttrTag(String value, String methodName) {
        this.value = value;
        this.methodName = methodName;
    }

    public String getValue() {
        return value;
    }

    public String getAttrValue(PsiMethod psiMethod) throws InvocationTargetException, IllegalAccessException {
        Class<? extends PsiMethod> aClass = psiMethod.getClass();
        Method[] methods = aClass.getMethods();
        for (Method method : methods) {
            if (Objects.equals(method.getName(), methodName)) {
                return method.invoke(psiMethod, null).toString();
            }
        }
        return null;
    }
}
