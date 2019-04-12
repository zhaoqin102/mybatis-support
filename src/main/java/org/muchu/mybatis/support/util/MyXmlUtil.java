package org.muchu.mybatis.support.util;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.CommonProcessors;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.DomFileElement;
import com.intellij.util.xml.DomService;
import org.muchu.mybatis.support.model.Mapper;
import org.muchu.mybatis.support.model.MapperIdentifiableStatement;

import java.util.List;
import java.util.Objects;

/**
 * @author heber
 */
public class MyXmlUtil {


    public static DomElement process(PsiElement psiElement) {
        if (psiElement instanceof PsiClass) {
            return processClass((PsiClass) psiElement);
        } else if (psiElement instanceof PsiMethod) {
            return processMethod((PsiMethod) psiElement);
        }
        return null;
    }


    private static Mapper processClass(PsiClass psiClass) {
        if (psiClass != null && psiClass.isInterface()) {
            Project project = psiClass.getProject();
            CommonProcessors.FindProcessor<DomFileElement<Mapper>> processor = new CommonProcessors.FindProcessor<DomFileElement<Mapper>>() {
                @Override
                protected boolean accept(DomFileElement<Mapper> domFileElement) {
                    Mapper mapper = domFileElement.getRootElement();
                    return Objects.equals(psiClass.getQualifiedName(), mapper.getNamespace().getRawText());
                }
            };
            List<DomFileElement<Mapper>> fileElements = DomService.getInstance().getFileElements(Mapper.class, project, GlobalSearchScope.allScope(project));
            for (DomFileElement<Mapper> fileElement : fileElements) {
                processor.process(fileElement);
                if (processor.isFound()) {
                    return Objects.requireNonNull(processor.getFoundValue()).getRootElement();
                }
            }
        }
        return null;
    }

    private static MapperIdentifiableStatement processMethod(PsiMethod psiMethod) {
        PsiClass psiClass = psiMethod.getContainingClass();
        Mapper mapper = processClass(psiClass);
        MapperIdentifiableStatement findStatement = null;
        if (mapper != null) {
            for (MapperIdentifiableStatement statement : mapper.getIdentifiableStatements()) {
                if (Objects.equals(psiMethod.getName(), statement.getId().getRawText())) {
                    findStatement = statement;
                }
            }
        }
        return findStatement;
    }
}
