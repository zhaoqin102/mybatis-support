package org.muchu.mybatis.support.typedHandler;

import com.intellij.codeInsight.AutoPopupController;
import com.intellij.codeInsight.template.impl.editorActions.TypedActionHandlerBase;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.TypedActionHandler;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiUtilBase;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.muchu.mybatis.support.constant.MyBatisTag;

public class MyTypedHandler extends TypedActionHandlerBase {

    public MyTypedHandler(@Nullable TypedActionHandler originalHandler) {
        super(originalHandler);
    }

    @Override
    public void execute(@NotNull Editor editor, char charTyped, @NotNull DataContext dataContext) {
        final Project project = CommonDataKeys.PROJECT.getData(dataContext);
        PsiFile psiFile = project == null ? null : PsiUtilBase.getPsiFileInEditor(editor, project);
        boolean isMybatisFile = false;
        if (psiFile instanceof XmlFile) {
            XmlFile xmlFile = (XmlFile) psiFile;
            XmlTag rootTag = xmlFile.getRootTag();
            isMybatisFile = rootTag != null && MyBatisTag.MAPPER.getValue().equals(rootTag.getName());
        }
        if (isMybatisFile) {
        }
        if (myOriginalHandler != null) {
            myOriginalHandler.execute(editor, charTyped, dataContext);
        }
    }
}
