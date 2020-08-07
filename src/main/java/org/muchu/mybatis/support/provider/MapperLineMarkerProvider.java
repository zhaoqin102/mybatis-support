package org.muchu.mybatis.support.provider;

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiIdentifier;
import com.intellij.psi.PsiMethod;
import org.jetbrains.annotations.NotNull;
import org.muchu.mybatis.support.icon.MybatisIcon;
import org.muchu.mybatis.support.service.FindRelatedItemService;
import org.muchu.mybatis.support.service.factory.MyFindRelatedItemServiceFactory;
import org.muchu.mybatis.support.util.PsiElementUtil;

import java.util.Collection;
import java.util.List;

public class MapperLineMarkerProvider extends RelatedItemLineMarkerProvider {

    @Override
    protected void collectNavigationMarkers(@NotNull PsiElement element, @NotNull Collection<? super RelatedItemLineMarkerInfo<?>> result) {
        boolean support = isSupport(element);
        if (!support) {
            return;
        }
        FindRelatedItemService findRelatedItemService = MyFindRelatedItemServiceFactory.getFindRelatedItemService(element);
        List<PsiElement> resultList = findRelatedItemService.findRelatedItem(element);
        if (resultList.size() > 0) {
            NavigationGutterIconBuilder<PsiElement> builder =
                    NavigationGutterIconBuilder.create(MybatisIcon.NAVIGATE_TO_XML).
                            setTargets(resultList).
                            setTooltipText("Navigate to mybatis file");
            result.add(builder.createLineMarkerInfo(element));
        }
    }

    private boolean isSupport(PsiElement element) {
        PsiElement parent = element.getParent();
        if (!(element instanceof PsiIdentifier) || (!(parent instanceof PsiMethod) && !(parent instanceof PsiClass))) {
            return false;
        }
        PsiClass psiClass = PsiElementUtil.getPsiClass(element);
        return psiClass != null && psiClass.isInterface();
    }
}
