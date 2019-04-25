package cn.devmgr.javathreads.section3;

/**
 * 适合 volatile 的使用场景；
 *
 *
 * 可以通过增加 volatile 关键字修饰 shouldExit 变量解决的问题
 */
public class VolatileTest1 {

    private boolean shouldExit = false;

    public void setShouldExit(boolean newValue){
        System.out.println("[" + Thread.currentThread().getName() + "] setShouldExit to " + newValue);
        shouldExit = newValue;
    }

    public void waitForExit(){
        System.out.println("[" + Thread.currentThread().getName() + "] waitingForExit()");
        while(!shouldExit){
            // 执行线程内操作
        }
        System.out.println("[" + Thread.currentThread().getName() + "] waitForExit end");
    }

    public static void main(String[] argvs) throws Exception{
        final VolatileTest1 vt = new VolatileTest1();
        new Thread(() -> {
            vt.waitForExit();
        }, "T-1").start();
        vt.setShouldExit(true);

        Thread t = new Thread(() -> {
            while(true){
                // do somthing
            }
        }, "T-1");
        t.setDaemon(true);
        t.start();
    }
}
