package org.muchu.mybatis.support.util;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.xml.DomFileElement;
import com.intellij.util.xml.DomService;
import com.intellij.util.xml.GenericAttributeValue;
import org.muchu.mybatis.support.bean.CommonAttribute;
import org.muchu.mybatis.support.bean.Mapper;
import org.muchu.mybatis.support.bean.Id;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyXmlUtil {

    public static List<XmlTag> process(PsiElement element) {
        List<XmlTag> resultList = new ArrayList<>();
        PsiClass psiClass = PsiElementUtil.getPsiClass(element);
        List<DomFileElement<Mapper>> fileElements = DomService.getInstance().getFileElements(Mapper.class, element.getProject(), GlobalSearchScope.allScope(element.getProject()));
        for (DomFileElement<Mapper> fileElement : fileElements) {
            Mapper mapper = fileElement.getRootElement();
            GenericAttributeValue<String> nameSpace = mapper.getNameSpace();
            if (nameSpace != null) {
                if (Objects.equals(nameSpace.getRawText(), psiClass.getQualifiedName())) {
                    if (element.getParent() instanceof PsiClass) {
                        resultList.add(nameSpace.getXmlTag());
                    } else if (element.getParent() instanceof PsiMethod) {
                        PsiMethod psiMethod = (PsiMethod) element.getParent();
                        List<CommonAttribute> identifiableStatements = fileElement.getRootElement().getCommonAttributes();
                        if (identifiableStatements != null) {
                            for (Id identifiableStatement : identifiableStatements) {
                                if (identifiableStatement.getId() != null) {
                                    if (Objects.equals(identifiableStatement.getId().getRawText(), psiMethod.getName())) {
                                        resultList.add(identifiableStatement.getId().getXmlTag());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return resultList;
    }
}
