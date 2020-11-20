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
      if (psiClass != null) {
        List<Mapper> mappers = MyDomService.getInstance().getMapper(psiClass, GlobalSearchScope.allScope(element.getProject()));
        return !mappers.isEmpty();
      }
    }
    return false;
  }

  @Override
  public void invoke(@NotNull Project project, Editor editor, @NotNull PsiElement element) throws IncorrectOperationException {
    PsiClass psiClass = PsiTreeUtil.getParentOfType(element, PsiClass.class);
    if (psiClass == null) {
      return;
    }
    PsiElement context = element.getContext();
    if (context instanceof PsiMethod) {
      ListPopup listPopup = JBPopupFactory.getInstance().createListPopup(new BaseListPopupStep<MyBatisSQLTag>(null, MyBatisSQLTag.values()) {

        @NotNull
        @Override
        public String getTextFor(MyBatisSQLTag value) {
          return value.getValue() + " statement";
        }

        @Nullable
        @Override
        public PopupStep onChosen(MyBatisSQLTag selectedValue, boolean finalChoice) {
          PsiMethod psiMethod = (PsiMethod) context;
//          Mapper mapper = MyDomService.getInstance().getMapper(psiClass, GlobalSearchScope.allScope(element.getProject()));
//          generateStatement(selectedValue, psiMethod, mapper, project);
          return super.onChosen(selectedValue, finalChoice);
        }
      });
      listPopup.showInBestPositionFor(editor);
    }
  }

  private void generateStatement(MyBatisSQLTag myBatisSQLTag, PsiMethod psiMethod, Mapper mapper, Project project) {
    WriteCommandAction.writeCommandAction(project).run(() -> {
      Statement statement = null;
      switch (myBatisSQLTag) {
        case INSERT:
          statement = mapper.addInsert();
          break;
        case DELETE:
          statement = mapper.addDelete();
          break;
        case UPDATE:
          statement = mapper.addUpdate();
          break;
        case SELECT:
          statement = mapper.addSelect();
          String returnTypeStr = "";
          PsiType returnType = psiMethod.getReturnType();
          if (returnType instanceof PsiClassType) {
            PsiClassType psiClassType = ((PsiClassType) returnType);
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
          } else if (returnType instanceof PsiArrayType) {
            PsiArrayType psiArrayType = (PsiArrayType) returnType;
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
      statement.setValue(myBatisSQLTag.getBodyText());
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
//            CodeStyleManager formatter = CodeStyleManager.getInstance(project);
//            formatter.reformat(mapper.getXmlTag().getContainingFile());
    });
  }

}
