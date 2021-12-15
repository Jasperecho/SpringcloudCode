package com.yjj.lb;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MyLB implements LoadBalancer{

    private AtomicInteger atomicInteger = new AtomicInteger(0);
    public final int getAndIncrement(){
        int current;
        int next;
        do {
            current = this.atomicInteger.get();//得到初始的值为0
            next = current >= 2147483647 ? 0: current + 1;//如果是false则next值为1
        }while (!this.atomicInteger.compareAndSet(current,next));//算出是否一样，0和1不一样
        //则为ture前面！为false就跳出循环，自旋锁的实现方式就是这样
        System.out.println("-------第几次访问，次数next: "+next);
        return next;
    }
    @Override
    public ServiceInstance instances(List<ServiceInstance> serviceInstances) {
        int index = getAndIncrement() % serviceInstances.size();//当前服务list下标index
        //serviceInstances.size()为list的个数，取余算出index，实现轮询
        return serviceInstances.get(index);
    }
}
