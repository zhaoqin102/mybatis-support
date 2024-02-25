package org.muchu.mybatis.support.intention;

import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.ui.popup.ListPopup;
import com.intellij.openapi.ui.popup.PopupStep;
import com.intellij.openapi.ui.popup.util.BaseListPopupStep;
import com.intellij.pom.Navigatable;
import com.intellij.psi.*;
import com.intellij.psi.presentation.java.SymbolPresentationUtil;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlTag;
import com.intellij.psi.xml.XmlTokenType;
import com.intellij.util.IncorrectOperationException;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.muchu.mybatis.support.constant.MyBatisSQLTag;
import org.muchu.mybatis.support.dom.model.Mapper;
import org.muchu.mybatis.support.dom.model.Select;
import org.muchu.mybatis.support.dom.model.Statement;
import org.muchu.mybatis.support.service.MyDomService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.intellij.psi.CommonClassNames.JAVA_UTIL_COLLECTION;
import static com.intellij.psi.CommonClassNames.JAVA_UTIL_MAP;

public class GenerateStatementAction extends PsiElementBaseIntentionAction {

  @Nls(capitalization = Nls.Capitalization.Sentence)
  @NotNull
  @Override
  public String getText() {
    return "Create statement";
  }

  @Nls(capitalization = Nls.Capitalization.Sentence)
  @NotNull
  @Override
  public String getFamilyName() {
    return getText();
  }

  @Override
  public boolean isAvailable(@NotNull Project project, Editor editor, @NotNull PsiElement element) {
    if (element.getContainingFile() instanceof PsiJavaFile) {
      if (!(element.getContext() instanceof PsiMethod)) {
        return false;
      }
      PsiClass psiClass = PsiTreeUtil.getParentOfType(element, PsiClass.class);
      if (psiClass == null || !psiClass.isInterface()) {
        return false;
      }
      PsiMethod psiMethod = PsiTreeUtil.getParentOfType(element, PsiMethod.class);
      if (psiMethod != null) {
        Statement statement = MyDomService.getInstance().getStatement(psiMethod, GlobalSearchScope.allScope(element.getProject()));
        return statement == null;
      }
    }
    return false;
  }

  @Override
  public void invoke(@NotNull Project project, Editor editor, @NotNull PsiElement element) throws IncorrectOperationException {
    if (!element.isPhysical()) {
      return;
    }
    PsiClass psiClass = PsiTreeUtil.getParentOfType(element, PsiClass.class);
    if (psiClass == null) {
      return;
    }
    List<Mapper> mappers = MyDomService.getInstance().getMapper(psiClass, GlobalSearchScope.allScope(element.getProject()));
    if (mappers.isEmpty()) {
      return;
    }
    List<Choose> chooses = Choose.createChoose(mappers);
    ListPopup listPopup = JBPopupFactory.getInstance().createListPopup(new BaseListPopupStep<>(null, chooses) {
      @NotNull
      @Override
      public String getTextFor(Choose value) {
        String path = "";
        XmlTag xmlTag = value.getMapper().getXmlTag();
        if (xmlTag != null && xmlTag.getContainingFile() != null) {
          path = SymbolPresentationUtil.getFilePathPresentation(xmlTag.getContainingFile());
        }
        return value.getMyBatisSQLTag().getValue() + " in " + path;
      }

      @Nullable
      @Override
      public PopupStep<?> onChosen(Choose selectedValue, boolean finalChoice) {
        PsiMethod psiMethod = PsiTreeUtil.getParentOfType(element, PsiMethod.class);
        generateStatement(selectedValue, psiMethod, project);
        return super.onChosen(selectedValue, finalChoice);
      }
    });
    listPopup.showInBestPositionFor(editor);
  }

  private void generateStatement(Choose choose, PsiMethod psiMethod, Project project) {
    WriteCommandAction.writeCommandAction(project).run(() -> {
      Statement statement = null;
      switch (choose.getMyBatisSQLTag()) {
        case INSERT:
          statement = choose.getMapper().addInsert();
          break;
        case DELETE:
          statement = choose.getMapper().addDelete();
          break;
        case UPDATE:
          statement = choose.getMapper().addUpdate();
          break;
        case SELECT:
          statement = choose.getMapper().addSelect();
          String returnTypeStr = "";
          PsiType returnType = psiMethod.getReturnType();
          if (returnType instanceof PsiClassType psiClassType) {
            PsiType collectionType = Optional.ofNullable(JavaPsiFacade.getInstance(project).findClass(JAVA_UTIL_COLLECTION, GlobalSearchScope.allScope(project)))
                .map(psiClass -> PsiElementFactory.getInstance(project).createType(psiClass))
                .orElse(null);
            PsiType mapType = Optional.ofNullable(JavaPsiFacade.getInstance(project).findClass(JAVA_UTIL_MAP, GlobalSearchScope.allScope(project)))
                .map(psiClass -> PsiElementFactory.getInstance(project).createType(psiClass))
                .orElse(null);
            if (collectionType != null && collectionType.isAssignableFrom(returnType) && psiClassType.getParameterCount() == 1) {
              returnTypeStr = psiClassType.getParameters()[0].getCanonicalText();
            } else if (mapType != null && mapType.isAssignableFrom(returnType)) {
              PsiClass resolve = psiClassType.resolve();
              if (resolve != null) {
                returnTypeStr = resolve.getQualifiedName();
              }
            } else {
              returnTypeStr = psiClassType.getCanonicalText();
            }
          } else if (returnType instanceof PsiArrayType psiArrayType) {
            returnTypeStr = psiArrayType.getComponentType().getCanonicalText();
          }
          if (StringUtils.isNotBlank(returnTypeStr)) {
            ((Select) statement).getResultType().setStringValue(returnTypeStr);
          }
      }
      if (statement == null) {
        return;
      }
      statement.getId().setStringValue(psiMethod.getName());
      statement.setValue(choose.getMyBatisSQLTag().getBodyText());
      XmlTag xmlTag = statement.getXmlTag();
      if (xmlTag != null) {
        ASTNode node = xmlTag.getNode();
        ASTNode xmlName = node.findChildByType(XmlTokenType.XML_NAME);
        if (xmlName != null) {
          ((Navigatable) xmlName.getPsi().getNavigationElement()).navigate(true);
          Editor selectedTextEditor = FileEditorManager.getInstance(project).getSelectedTextEditor();
          if (selectedTextEditor != null) {
            selectedTextEditor.getCaretModel()
                .moveCaretRelatively(2, 0, false, false, false);
          }
        }
      }
    });
  }

  public static class Choose {
    private Mapper mapper;
    private MyBatisSQLTag myBatisSQLTag;

    public Choose(Mapper mapper, MyBatisSQLTag myBatisSQLTag) {
      this.mapper = mapper;
      this.myBatisSQLTag = myBatisSQLTag;
    }

    public Mapper getMapper() {
      return mapper;
    }

    public void setMapper(Mapper mapper) {
      this.mapper = mapper;
    }

    public MyBatisSQLTag getMyBatisSQLTag() {
      return myBatisSQLTag;
    }

    public void setMyBatisSQLTag(MyBatisSQLTag myBatisSQLTag) {
      this.myBatisSQLTag = myBatisSQLTag;
    }

    public static List<Choose> createChoose(@NotNull List<Mapper> mappers) {
      List<Choose> chooses = new ArrayList<>();
      for (Mapper mapper : mappers) {
        for (MyBatisSQLTag myBatisSQLTag : MyBatisSQLTag.values()) {
          Choose choose = new Choose(mapper, myBatisSQLTag);
          chooses.add(choose);
        }
      }
      return chooses;
    }
  }

}
