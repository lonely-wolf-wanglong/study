package com.example.demo.annotation;


import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

    /**
     * 操作模块
     * @return
     */
    String operModule() default "";

    /**
     * 操作菜单
     * @return
     */
    String operMenu() default "";

    /**
     * 操作
     * @return
     */
    String operName() default "";

    /**
     * 操作描述
     * @return
     */
    String operDesc() default "";
}
