package com.yuhao.haorpc.loadbalancer;

import com.yuhao.haorpc.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 负载均衡算法：最少活跃数
 */
public class LeastActiveLoadBalancer implements LoadBalancer{

    private final Random random = new Random();
    //todo
    @Override
    public ServiceMetaInfo select(Map<String, Object> requestParams, List<ServiceMetaInfo> serviceMetaInfoList) {
//        //创建一个map集合用于维护活跃数和权重
//        Map
//        int length = serviceMetaInfoList.size(); // 总个数
//        int leastActive = -1; // 最小的活跃数
//        int leastCount = 0; // 相同最小活跃数的个数
//        int[] leastIndexs = new int[length]; // 相同最小活跃数的下标
//        int totalWeight = 0; // 总权重
//        int firstWeight = 0; // 第一个权重，用于于计算是否相同
//        boolean sameWeight = true; // 是否所有权重相同
//        for (int i = 0; i < length; i++) {
//            ServiceMetaInfo<T> s = invokers.get(i);
//            int active = RpcStatus.getStatus(invoker.getUrl(), invocation.getMethodName()).getActive(); // 活跃数
//            int weight = invoker.getUrl().getMethodParameter(invocation.getMethodName(), Constants.WEIGHT_KEY, Constants.DEFAULT_WEIGHT); // 权重
//            if (leastActive == -1 || active < leastActive) { // 发现更小的活跃数，重新开始
//                leastActive = active; // 记录最小活跃数
//                leastCount = 1; // 重新统计相同最小活跃数的个数
//                leastIndexs[0] = i; // 重新记录最小活跃数下标
//                totalWeight = weight; // 重新累计总权重
//                firstWeight = weight; // 记录第一个权重
//                sameWeight = true; // 还原权重相同标识
//            } else if (active == leastActive) { // 累计相同最小的活跃数
//                leastIndexs[leastCount ++] = i; // 累计相同最小活跃数下标
//                totalWeight += weight; // 累计总权重
//                // 判断所有权重是否一样
//                if (sameWeight && i > 0
//                        && weight != firstWeight) {
//                    sameWeight = false;
//                }
//            }
//        }
//        // assert(leastCount > 0)
//        if (leastCount == 1) {
//            // 如果只有一个最小则直接返回
//            return invokers.get(leastIndexs[0]);
//        }
//        if (! sameWeight && totalWeight > 0) {
//            // 如果权重不相同且权重大于0则按总权重数随机
//            int offsetWeight = random.nextInt(totalWeight);
//            // 并确定随机值落在哪个片断上
//            for (int i = 0; i < leastCount; i++) {
//                int leastIndex = leastIndexs[i];
//                offsetWeight -= getWeight(invokers.get(leastIndex), invocation);
//                if (offsetWeight <= 0)
//                    return invokers.get(leastIndex);
//            }
//        }
//        // 如果权重相同或权重为0则均等随机
//        return invokers.get(leastIndexs[random.nextInt(leastCount)]);
        return null;
    }
}
