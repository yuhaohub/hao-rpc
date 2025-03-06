package com.yuhao.haorpc.utils;


import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;

import java.util.Arrays;
import java.util.List;

/**
 * 配置工具类，加载配置
 */
public class ConfigUtils {

    private static final List<String> SUPPORTED_EXTENSIONS = Arrays.asList(".properties", ".yml", ".yaml");
    public static <T> T loadConfig(Class<T> rpcClass,String prefix) {
        return loadConfig(rpcClass,prefix,"");

    }


    public static <T> T loadConfig(Class<T> rpcClass,String prefix,String environment) {

        StringBuilder baseFileName = new StringBuilder("application");
        if (StrUtil.isNotBlank(environment)) {
            baseFileName.append("-").append(environment);
        }
    //todo 优化配置文件加载
//        for (String extension : SUPPORTED_EXTENSIONS) {
//            String configFileName = baseFileName + extension;
//            try {
//                if (".properties".equals(extension)) {
//                    Props props = new Props(configFileName);
//                    props.autoLoad(true);
//                    return props.toBean(rpcClass, prefix);
//                } else if (".yml".equals(extension) || ".yaml".equals(extension)) {
//                    Dict dict = YamlUtil.loadByPath(configFileName);
//                    if (dict != null) {
//                        return BeanUtil.copyProperties(dict.getBean(prefix), rpcClass);
//                    }
//                }
//            } catch (Exception e) {
//                // 若加载失败，继续尝试下一个后缀的文件
//                continue;
//            }
//        }
        String configFileName = baseFileName + ".properties";
        Props props = new Props(configFileName);
        props.autoLoad(true);
        return props.toBean(rpcClass, prefix);
    }



}
