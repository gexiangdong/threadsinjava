package cn.devmgr.javathreads.section3;


public class SelfIncreasement {
    private long counter = 0;

    public void add() {
        synchronized (this) {
            counter++;
        }
    }

    public static void main(String[] argvs) throws Exception {
        SelfIncreasement si = new SelfIncreasement();
        Thread[] threads = new Thread[10000];
        for(int i=0; i<threads.length; i++){
            threads[i] = new Thread(() -> {
                for(int j=0; j<10000; j++){
                    si.add();
                }
            }, "T-" + i);
            threads[i].start();
        }
        for(int i=0; i<threads.length; i++){
            threads[i].join();
        }
        //期望是 10000 （1万个线程） * 10000 （每个线程自加1万次） = 100000000 （1亿）
        System.out.println(si.counter);
    }
}