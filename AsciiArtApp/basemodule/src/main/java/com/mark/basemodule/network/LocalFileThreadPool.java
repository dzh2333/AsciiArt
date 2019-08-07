package com.mark.basemodule.network;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class LocalFileThreadPool {
    public static final int ARRAY_QUEUE = 0; //ArrayBlockingQueue  有界队列策略
    public static final int LINKED_QUEUE = 1; //LinkedBlockingQueue 使用无界队列策略

    private ThreadPoolExecutor executor;
    private BlockingQueue<Runnable> workQueue;

    /**
     * @param workQueneSize
     *            队列长度
     * @param coreSize
     *            主线程数
     * @param maxSize
     *            最大线程数
     * @param queueType
     *            队列类型
     */
    public LocalFileThreadPool(final int workQueneSize, final int coreSize,
                               final int maxSize, int queueType)
    {
        this(workQueneSize, coreSize, maxSize, queueType, null);
    }

    /**
     *
     * @param workQueneSize
     *            队列长度
     * @param coreSize
     *            主线程数
     * @param maxSize
     *            最大线程数
     * @param queueType
     *            队列类型
     * @param policy
     *            处理策略
     */
    public LocalFileThreadPool(final int workQueneSize, final int coreSize,
                               final int maxSize, int queueType, RejectedExecutionHandler policy)
    {
        workQueue = createQueue(queueType, workQueneSize);
        executor = new ThreadPoolExecutor(coreSize, maxSize, 60,
                TimeUnit.SECONDS, workQueue, policy != null ? policy
                : new ThreadPoolExecutor.AbortPolicy());
    }

    public void execute(Runnable runnable)
    {
        if (workQueue.size() > 4)
        {

            System.out.println("当前等待线程大小：'"+runnable.getClass().getSimpleName()+
                    "':"+workQueue.size());
        }
        executor.execute(runnable);
    }

    /**
     * 创建队列，选择不同和队列策略
     * ArrayBlockingQueue  有界队列策略
     * LinkedBlockingQueue 使用无界队列策略
     * @param queueType
     * @param queueSize
     * @return
     */
    private BlockingQueue<Runnable> createQueue(int queueType, int queueSize)
    {
        return queueType == LINKED_QUEUE ? new LinkedBlockingQueue<Runnable>(
                queueSize) : new ArrayBlockingQueue<Runnable>(queueSize);
    }

    public BlockingQueue<Runnable> getQueue()
    {
        return executor.getQueue();
    }
}
