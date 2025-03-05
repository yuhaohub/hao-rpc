package com.yuhao.haorpc.utils;


import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;

/**
 * 配置工具类，加载配置
 */
public class ConfigUtils {
    public static <T> T loadConfig(Class<T> rpcClass,String prefix) {
        return loadConfig(rpcClass,prefix,"");

    }


    public static <T> T loadConfig(Class<T> rpcClass,String prefix,String environment) {
        StringBuilder configFile = new StringBuilder("application");
        if(StrUtil.isNotBlank(environment)){
            configFile.append("-").append(environment);
        }
        configFile.append(".properties");
        Props props = new Props(configFile.toString());
        props.autoLoad(true);
        return props.toBean(rpcClass,prefix);


//        configFile.append(".yml");
//        // 使用 YamlUtil 加载配置文件并转换为 Dict 对象
//        Dict dict = YamlUtil.loadByPath(configFile.toString());
//        // 获取指定前缀的配置项并转换为目标类实例
//        T configInstance = BeanUtil.copyProperties(dict.getBean(prefix), rpcClass);
//
//        return configInstance;

    }

}
