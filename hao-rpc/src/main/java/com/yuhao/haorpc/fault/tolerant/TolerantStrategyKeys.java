package com.yuhao.haorpc.fault.tolerant;

public interface TolerantStrategyKeys {
    /**
     * 快速失败
     */
    String FAIL_FAST = "failFast";

    /**
     * 静默处理
     */
    String FAIL_SAFE = "failSafe";

}
