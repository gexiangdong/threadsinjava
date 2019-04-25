package cn.devmgr.javathreads.section2;

public class Sync0 {

    public static void main(String[] argvs){
        Runnable runnable = new Runnable() {
            @Override
            public  void run() {
                synchronized (this) {
                    for (int i = 0; i < 10; i++) {
                        System.out.print(i);
                    }
                }
            }
        };

        new Thread(runnable, "T1").start();
        new Thread(runnable, "T2").start();
    }
}
