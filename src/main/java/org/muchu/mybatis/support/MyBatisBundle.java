package org.muchu.mybatis.support;

import com.intellij.DynamicBundle;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.PropertyKey;

public class MyBatisBundle extends DynamicBundle {
  private static final String PATH_TO_BUNDLE = "messages.MyBatisBundle";
  private static final MyBatisBundle ourInstance = new MyBatisBundle();

  private MyBatisBundle() {
    super(PATH_TO_BUNDLE);
  }

  public static @Nls String message(@NotNull @PropertyKey(resourceBundle = PATH_TO_BUNDLE) String key,
                                    Object @NotNull ... params) {
    return ourInstance.getMessage(key, params);
  }
}
