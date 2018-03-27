package org.muchu.mybatis.support.description;

import com.intellij.openapi.module.Module;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.xml.DomFileDescription;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.muchu.mybatis.support.model.Mapper;

/**
 * @author Max
 */
public class MapperDescription extends DomFileDescription<Mapper> {

    public MapperDescription() {
        super(Mapper.class, "mapper");
    }

    @Override
    public boolean isMyFile(@NotNull XmlFile file, @Nullable Module module) {
        XmlTag rootTag = file.getRootTag();
        return rootTag != null && rootTag.getName().equals(getRootTagName());
    }

}
