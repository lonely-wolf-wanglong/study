package com.example.demo.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.time.LocalDateTime;
import java.util.Optional;
@Component
public class DemoMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        fillByName("delFlag",  "0",metaObject );
        fillByName("createUser",  getUserName(),metaObject );
        fillByName("createTime", LocalDateTime.now() , metaObject);
        fillByName("updateUser", getUserName() , metaObject);
        fillByName("updateTime", LocalDateTime.now()  , metaObject);
        fillByName("version",  0 ,metaObject );
    }

    private void fillByName(String fieldName, Object fieldVal, MetaObject metaObject) {
        Class<?> getterType = metaObject.getGetterType(fieldName);
        Object obejct = metaObject.getValue(fieldName);
        if (ClassUtils.isAssignableValue(getterType, fieldVal) && (obejct == null || obejct == "")) {
            metaObject.setValue(fieldName, fieldVal);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateUser", getUserName() , metaObject);
        this.setFieldValByName("updateTime", LocalDateTime.now()  , metaObject);
    }

    private String getUserName() {
        // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 通过springSecurity中的SecurityContextHolder获取用户信息，然后返回
       return "UserName";
    }
}
