package cn.simbrain.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author huowei
 * @version 1.0.0
 * @description 自动填写活动创建时间以及更新时间
 * @date 2021/2/12
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("actCreate",new Date(),metaObject);
        this.setFieldValByName("actUpdate",new Date(),metaObject);
        this.setFieldValByName("userCreate",new Date(),metaObject);
        this.setFieldValByName("userUpdate",new Date(),metaObject);
        this.setFieldValByName("sysCreate",new Date(),metaObject);
        this.setFieldValByName("sysUpdate",new Date(),metaObject);
        this.setFieldValByName("poCreate",new Date(),metaObject);
        this.setFieldValByName("poUpdate",new Date(),metaObject);

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("actUpdate",new Date(),metaObject);
        this.setFieldValByName("userUpdate",new Date(),metaObject);
        this.setFieldValByName("sysUpdate",new Date(),metaObject);
        this.setFieldValByName("poUpdate",new Date(),metaObject);
    }
}
