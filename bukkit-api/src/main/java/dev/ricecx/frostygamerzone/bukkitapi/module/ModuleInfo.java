package dev.ricecx.frostygamerzone.bukkitapi.module;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleInfo {
    String configName() default "";
    Class<? extends ModuleConfig> configClass() default ModuleConfig.class;
    Class<? extends AbstractPlayerConfig> playerConfigClass() default AbstractPlayerConfig.class;
}
