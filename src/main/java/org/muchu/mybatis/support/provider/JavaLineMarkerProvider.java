package org.muchu.mybatis.support.provider;

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.muchu.mybatis.support.icon.MybatisIcon;
import org.muchu.mybatis.support.service.FindRelatedItemService;
import org.muchu.mybatis.support.service.factory.MyFindRelatedItemServiceFactory;

import java.util.Collection;
import java.util.List;

public class JavaLineMarkerProvider extends RelatedItemLineMarkerProvider {

    @Override
    protected void collectNavigationMarkers(@NotNull PsiElement element, @NotNull Collection<? super RelatedItemLineMarkerInfo<?>> result) {
        FindRelatedItemService findRelatedItemService = MyFindRelatedItemServiceFactory.getFindRelatedItemService(element);
        List<PsiElement> resultList = findRelatedItemService.findRelatedItem(element);
        if (resultList.size() > 0) {
            NavigationGutterIconBuilder<PsiElement> builder =
                    NavigationGutterIconBuilder.create(MybatisIcon.NAVIGATE_TO_JAVA).
                            setTargets(resultList).
                            setTooltipText("Navigate to mybatis file");
            result.add(builder.createLineMarkerInfo(element));
        }
    }

}
