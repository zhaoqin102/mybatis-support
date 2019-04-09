package org.muchu.mybatis.support.intention;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.ListCellRendererWrapper;
import com.intellij.ui.TreeUIHelper;
import com.intellij.ui.components.JBList;
import org.jetbrains.annotations.Nullable;
import org.muchu.mybatis.support.constant.MyBatisTag;

import javax.swing.*;
import java.awt.*;

public class ChooseStatementDialog extends DialogWrapper {

    private final MyBatisTag[] myBatisTags = MyBatisTag.values();

    private final MyBatisTag defaultMyBatisTag;

    private JList<MyBatisTag> myList = new JBList<>();

    private JPanel myPanel = new JPanel();

    protected ChooseStatementDialog(final MyBatisTag defaultMyBatisTag) {
        super(false);
        this.defaultMyBatisTag = defaultMyBatisTag;
        init();
    }

    @Override
    @Nullable
    protected JComponent createCenterPanel() {
        AbstractListModel<MyBatisTag> model = new AbstractListModel<MyBatisTag>() {
            @Override
            public int getSize() {
                return myBatisTags.length;
            }

            @Override
            public MyBatisTag getElementAt(int i) {
                return myBatisTags[i];
            }
        };
        myList.setModel(model);
        TreeUIHelper.getInstance().installListSpeedSearch(myList);
        myList.setCellRenderer(new ListCellRendererWrapper<MyBatisTag>() {
            @Override
            public void customize(JList list, MyBatisTag value, int index, boolean selected, boolean hasFocus) {
                this.setText(value.getValue());
            }
        });
        if (defaultMyBatisTag != null) {
            myList.setSelectedValue(defaultMyBatisTag, true);
        }
        myPanel.setLayout(new BorderLayout());
        myPanel.setOpaque(false);
        myPanel.setBorder(BorderFactory.createEmptyBorder());
        myPanel.add(myList);
        return myPanel;
    }

    protected MyBatisTag getChosen() {
        return myList.getSelectedValue();
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return myList;
    }

}
