package cn.devmgr.javathreads.section3;

/**
 * volatile 关键字不适合的场景
 *
 * volatile 不能保证代码的原子性，counter++ 自身不是原子性操作
 *
 * 可以用 synchronized 解决
 */
public class VolatleUnsuited {
    private volatile int counter = 0;

    public void add(){
        for(int i=0; i<10000; i++){
            counter ++;
        }
    }

    public static void main(String[] argvs) throws Exception{
        VolatleUnsuited vu = new VolatleUnsuited();
        Thread[] threads = new Thread[1000];
        for(int i = 0; i < threads.length; i++){
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    vu.add();
                }
            });
            threads[i].start();
        }
        for(int i = 0; i < threads.length; i++){
            threads[i].join();
        }

        System.out.println(vu.counter);

    }

}
