package com.mark.basemodule.network;

public class AppThreadPool {
    private LocalFileThreadPool tp;
    private static AppThreadPool pool = new AppThreadPool();

    private AppThreadPool()
    {
        int workQueneSize = 20;
        int coreSize = 10;
        int maxSize = 10;

        //创建线程池
        tp = new LocalFileThreadPool(workQueneSize, coreSize, maxSize,
                LocalFileThreadPool.ARRAY_QUEUE);
    }

    public static AppThreadPool getPool()
    {
        return pool;
    }

    public void execute(Runnable runnable)
    {
        tp.execute(runnable);
    }
}
