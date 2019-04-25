package cn.devmgr.javathreads.section4;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReentrantReadWriteLock.ReadLock 和 ReentrantReadWriteLock.WriteLock 都是可重入锁，也就是同一线程可以加多次。
 * 不同线程可以同时获取ReadLock，只有一个线程可获得WriteLock，且WirteLock时，其他线程不能获得ReadLock
 * 读锁是共享的，写锁是独占的、排它的
 *
 * ReentrantReadWriteLock不支持锁升级（ReadLock -> WriteLock)，支持锁降级(WriteLock -> ReadLock)
 *
 */
public class ReadWriteLockTest {
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public void readSomthing() throws InterruptedException {
        System.out.println("try readlock.   [" + Thread.currentThread().getName() + "]");
        lock.readLock().lock();
        try {
            System.out.print("(");
            Thread.sleep(100);
            System.out.println("readHoldCount: " + lock.getReadLockCount());
            Thread.sleep(100);
            System.out.print(")");
        }finally {
            lock.readLock().unlock();
        }
        System.out.println(Thread.currentThread().getName() + " unlock readLock");
    }

    public void writeSomething() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " try writeLock,  readHoldCount: " + lock.getReadLockCount()); //getReadLockCount是查询lock上已经加了多少次ReadLock
        lock.writeLock().lock();
        try {
            System.out.print("<");
            Thread.sleep(200);
            System.out.print(">");
        }finally {
            lock.writeLock().unlock();
        }
        System.out.println(Thread.currentThread().getName() + " unlock writeLock");
    }

    /**
     * 同一个线程内，先ReadLock再WriteLock是不可以的，即使确保没有其他线程同时持有读锁也也不行，会无限等待。
     * 但先持有写锁，后再加读锁是可以的。而且unlock也不需要特定顺序。
     */
    public void upgradeAndDowngradeLock(){
        lock.readLock().lock();
        System.out.println("已经获得readLock() " + lock.getReadLockCount());
        lock.readLock().unlock(); //如果没有这句，无法获得下面的writelock，即使同一个线程，也无其他读锁，也无法从readlock升级到writelock，这是死锁
        lock.writeLock().lock();
        System.out.println("已经获得writelock");
        lock.readLock().lock();     //在持有写锁的情况下，同一线程是可以获取读锁的。
        System.out.println("获得readLock()");
        lock.writeLock().unlock();  //可以先释放写锁；（先释放那个没有要求）
        System.out.println("已经释放writelock");
        lock.readLock().unlock();
        System.out.println("已经释放read锁");
    }

    public static void main(String[] argvs) throws Exception {
        ReadWriteLockTest rwlt = new ReadWriteLockTest();
        rwlt.upgradeAndDowngradeLock();
        for(int i=0; i<20; i++){
            final int index = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        if (index % 5 == 0){
                            rwlt.writeSomething();
                        }else{
                            rwlt.readSomthing();
                        }
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }, "T-" + i).start();
        }
    }
}
