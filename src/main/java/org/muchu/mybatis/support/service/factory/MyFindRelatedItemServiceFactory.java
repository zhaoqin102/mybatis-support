package org.muchu.mybatis.support.service.factory;

import com.intellij.psi.PsiElement;
import org.muchu.mybatis.support.service.FindRelatedItemService;
import org.muchu.mybatis.support.service.impl.*;

import java.util.ArrayList;
import java.util.List;

public class MyFindRelatedItemServiceFactory {
  private static final List<FindRelatedItemService> findRelatedItemServiceList = new ArrayList<>();

  static {
    findRelatedItemServiceList.add(MyFindMapperRelatedInterfaceService.INSTANCE);
    findRelatedItemServiceList.add(MyFindStatementRelatedMethodService.INSTANCE);
    findRelatedItemServiceList.add(MyFindInterfaceRelatedXmlTagService.INSTANCE);
    findRelatedItemServiceList.add(MyFindMethodRelatedXmlTagService.INSTANCE);
  }

  public static FindRelatedItemService getFindRelatedItemService(PsiElement psiElement) {
    for (FindRelatedItemService findRelatedItemService : findRelatedItemServiceList) {
      if (findRelatedItemService.isSupport(psiElement)) {
        return findRelatedItemService;
      }
    }
    return MyNotSupportElementService.INSTANCE;
  }
}
