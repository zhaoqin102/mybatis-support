package org.muchu.mybatis.support.language;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.muchu.mybatis.support.icon.MybatisIcon;

import javax.swing.*;

public class MybatisFileType extends LanguageFileType {


    private MybatisFileType() {
        super(MybatisLanguage.INSTANCE, true);
    }

    @NotNull
    @Override
    public String getName() {
        return "Mybatis file";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Mybatis language file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "xml";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return MybatisIcon.MYBATIS_ICON;
    }
}
