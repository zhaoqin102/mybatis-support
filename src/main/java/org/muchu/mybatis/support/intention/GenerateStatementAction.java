package org.muchu.mybatis.support.intention;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

public class GenerateStatementAction implements IntentionAction {

    private String name = "Create statement";


    @Nls(capitalization = Nls.Capitalization.Sentence)
    @NotNull
    @Override
    public String getText() {
        return name;
    }

    @Nls(capitalization = Nls.Capitalization.Sentence)
    @NotNull
    @Override
    public String getFamilyName() {
        return name;
    }

    @Override
    public boolean isAvailable(@NotNull Project project, Editor editor, PsiFile file) {
//        if (file instanceof PsiJavaFile) {
//            PsiElement element = MyJavaUtil.getPsiElement(editor, file);
//            PsiClass psiClass = PsiTreeUtil.getParentOfType(element, PsiClass.class);
//            if (psiClass == null || !psiClass.isInterface()) {
//                return false;
//            }
//            return element.getContext() instanceof PsiMethod && MyXmlUtil.process(element.getContext()) == null;
//        }
        return false;
    }

    @Override
    public void invoke(@NotNull Project project, Editor editor, PsiFile file) throws IncorrectOperationException {
//        PsiElement element = MyJavaUtil.getPsiElement(editor, file);
//        PsiClass psiClass = PsiTreeUtil.getParentOfType(element, PsiClass.class);
//        DomElement domElement = MyXmlUtil.process(psiClass);
//        if (domElement == null) {
//            HintManager.getInstance().showErrorHint(editor, "Please create mapper");
//            return;
//        }
//        PsiElement context = element.getContext();
//        XmlElement xmlElement = domElement.getXmlElement();
//        if (xmlElement instanceof XmlTag && context instanceof PsiMethod) {
//            ListPopup listPopup = JBPopupFactory.getInstance().createListPopup(new BaseListPopupStep<MyBatisSQLTag>(null, MyBatisSQLTag.values()) {
//
//                @NotNull
//                @Override
//                public String getTextFor(MyBatisSQLTag value) {
//                    return value.getValue() + " statement";
//                }
//
//                @Nullable
//                @Override
//                public PopupStep onChosen(MyBatisSQLTag selectedValue, boolean finalChoice) {
//                    PsiMethod psiMethod = (PsiMethod) context;
//                    XmlTag parent = (XmlTag) xmlElement;
//                    generateStatement(selectedValue, psiMethod, parent, project);
//                    return super.onChosen(selectedValue, finalChoice);
//                }
//            });
//            listPopup.showInBestPositionFor(editor);
//        }
    }

//    private void generateStatement(MyBatisSQLTag myBatisSQLTag, PsiMethod psiMethod, XmlTag parent, Project project) {
//        XmlTag childTag = myBatisSQLTag.createMyBatisTag(parent, psiMethod);
//        WriteCommandAction.runWriteCommandAction(project, "create statement", null, () -> {
//            parent.add(childTag);
//        }, parent.getContainingFile());
//        CodeStyleManager formatter = CodeStyleManager.getInstance(project);
//        formatter.reformat(parent.getContainingFile());
//    }

    @Override
    public boolean startInWriteAction() {
        return true;
    }
}
