package cn.devmgr.javathreads.section4;


import java.util.concurrent.locks.ReentrantLock;


/**
 * 可重入锁，被一个线程获得后，其他线程只能等待unlock后才可能获得。
 * 已获得锁的线程可以多次lock，多次lock需要多次unlock才能释放给其他线程
 *
 * 如果不确定是否完全unlock，可以用 getHoldCount()查询
 * while(lock.getHoldCount() > 0){
 *     lock.unlock();
 * }
 * 程序中应该用
 * lock.lock();
 * try{
 *     //do something
 * }finally{
 *     lock.unlock()
 * }
 * 的形式来确保每次锁都有对应的解锁
 */
public class LockTest {
    private ReentrantLock lock = new ReentrantLock();

    public void m1(){
        System.out.println("1.   [" + Thread.currentThread().getName() + "]");
        lock.lock();
        try {
            System.out.println("2.   [" + Thread.currentThread().getName() + "]");
            m2();
            Thread.sleep(100);
            System.out.println("3.   [" + Thread.currentThread().getName() + "]");
        }catch(InterruptedException e){
        }finally {
            lock.unlock();
            System.out.println("4.   [" + Thread.currentThread().getName() + "]");
        }
    }

    private void m2(){
        System.out.println("5.   [" + Thread.currentThread().getName() + "]");
        lock.lock();
        try {
            System.out.println("6.   [" + Thread.currentThread().getName() + "]");
            Thread.sleep(100);
        }catch(InterruptedException e){
        }finally {
            lock.unlock();
            System.out.println("7.   [" + Thread.currentThread().getName() + "]");
        }
    }

    public static void main(String[] argvs) throws Exception{
        final LockTest lt = new LockTest();
        new Thread(() -> {
            lt.m1();
        }, "T-1").start();
        new Thread(() -> {
            lt.m2();
        }, "T-2").start();
    }
}
