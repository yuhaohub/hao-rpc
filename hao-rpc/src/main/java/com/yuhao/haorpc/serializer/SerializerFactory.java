package com.yuhao.haorpc.serializer;

import com.yuhao.haorpc.spi.SpiLoader;

/**
 * 序列化器工厂（序列化器对象是可以复用的，使用工厂模式 + 单例模式 来简化创建和获取序列化器对象的操作）
 */
public class SerializerFactory {


    /**
     * 序列化器初始化(在类加载时就执行 SpiLoader.load(Serializer.class);饿汉式（Eager Initialization）)
     */
    static {
        SpiLoader.load(Serializer.class);
    }


    /**
     * 获取实例
     *
     * @param key
     * @return
     */
    public static Serializer getInstance(String key) {

        return SpiLoader.getInstance(Serializer.class, key);
    }
}
