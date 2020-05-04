package org.muchu.mybatis.support.description;

import com.intellij.util.xml.DomFileDescription;
import org.jetbrains.annotations.Nullable;
import org.muchu.mybatis.support.bean.Mapper;
import org.muchu.mybatis.support.constant.MyBatisDoctype;
import org.muchu.mybatis.support.icon.MybatisIcon;

import javax.swing.*;

public class MapperDescription extends DomFileDescription<Mapper> {

    public MapperDescription() {
        super(Mapper.class, "mapper");
    }

    @Nullable
    @Override
    public Icon getFileIcon(int flags) {
        return MybatisIcon.MYBATIS_ICON;
    }

    @Override
    protected void initializeFileDescription() {
        registerNamespacePolicy("mybatis", MyBatisDoctype.SYSTEM_ID);
    }
}