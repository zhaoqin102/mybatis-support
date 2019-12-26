package org.muchu.mybatis.support.constant;

import com.intellij.icons.AllIcons;
import com.intellij.ui.IconManager;
import org.muchu.mybatis.support.config.MybatisIconProvider;

import javax.swing.*;

/**
 * @author heber
 */
public class MybatisIcon {

    private static IconManager iconManager = IconManager.getInstance();

    public static final Icon NAVIGATE_TO_XML = AllIcons.Gutter.ImplementedMethod;

    public static final Icon NAVIGATE_TO_JAVA = AllIcons.Gutter.ImplementingMethod;

    public static final Icon MYBATIS_ICON = iconManager.getIcon("/images/mybatis.png", MybatisIcon.class);

}
