package com.yuhao.haorpc.fault.tolerant;

import com.yuhao.haorpc.spi.SpiLoader;

/**
 * 容错策略工厂
 */
public class TolerantStrategyFactory {

    static {
        SpiLoader.load(TolerantStrategy.class);
    }
    /**
     * 默认容错策略
     */
    private static final String DEFAULT_STRATEGY = "failfast";

    /**
     * 获取容错策略类实例
     * @param key
     * @return
     */
    public static TolerantStrategy getInstance(String key) {

        return SpiLoader.getInstance(TolerantStrategy.class, key);
    }
}
