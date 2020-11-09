package org.muchu.mybatis.support.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComponentWithBrowseButton;
import com.intellij.psi.PsiDirectory;
import com.intellij.ui.RecentsManager;
import com.intellij.ui.TextAccessor;
import com.intellij.ui.TextFieldWithHistory;
import org.jetbrains.annotations.NotNull;

import java.awt.event.ActionListener;
import java.util.List;

public class DirectoryEditorComboWithBrowseButton extends ComponentWithBrowseButton<TextFieldWithHistory> implements TextAccessor {

  public DirectoryEditorComboWithBrowseButton(final ActionListener browseActionListener,
                                              final PsiDirectory psiDirectory,
                                              @NotNull final Project project,
                                              final String recentsKey) {
    super(new TextFieldWithHistory(), browseActionListener);
    final List<String> recentEntries = RecentsManager.getInstance(project).getRecentEntries(recentsKey);
    if (recentEntries != null) {
      getChildComponent().setHistory(recentEntries);
    }
    if (psiDirectory != null && psiDirectory.getVirtualFile().getCanonicalFile() != null
        && psiDirectory.getVirtualFile().getCanonicalPath() != null && psiDirectory.getVirtualFile().getCanonicalPath().length() > 0) {
      setText(psiDirectory.getVirtualFile().getCanonicalPath());
    }
  }

  @Override
  public void setText(String text) {
    getChildComponent().setText(text);
  }

  @Override
  public String getText() {
    return getChildComponent().getText();
  }
}
