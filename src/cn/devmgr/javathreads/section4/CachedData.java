package cn.devmgr.javathreads.section4;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CachedData {
    private Data data;
    private long validUntil;
    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    public void processCachedData() {
        rwLock.readLock().lock();
        if (System.currentTimeMillis() > validUntil) {
            // 缓存失效，需要更新缓存
            rwLock.readLock().unlock();
            rwLock.writeLock().lock();
            try {
                // 重新检查是否缓存已经到期，因为可能在等待写锁期间，其他线程已经更新了缓存
                if (System.currentTimeMillis() > validUntil) {
                    // 更新缓存
                    data = new Data();
                    validUntil = System.currentTimeMillis();
                }
                // 先写锁；后读锁是允许的，准备锁降级
                rwLock.readLock().lock();
            } finally {
                rwLock.writeLock().unlock();
                //已经释放写锁，依旧持有读锁
            }
        }

        try {
            // 使用缓存的data进行业务处理
            use(data);
        } finally {
            // 释放读锁
            rwLock.readLock().unlock();
        }
    }

    private void use(Data date){

    }


    public static class Data{
        // 自定义的数据类
    }
}
