package cn.devmgr.javathreads.section2;

public class Sync2 {

    public void doA() throws InterruptedException{
        synchronized (Sync2.class) {
            System.out.println("[" + Thread.currentThread().getName() + "]<begin doA");
            Thread.currentThread().sleep(500);
            System.out.println("[" + Thread.currentThread().getName() + "]\t\tend doA>");
        }
    }

    public static synchronized void doB() throws InterruptedException{
        System.out.println("[" + Thread.currentThread().getName() + "]<begin doB");
        Thread.currentThread().sleep(500);
        System.out.println("[" + Thread.currentThread().getName() + "]\t\tend doB>");
    }

    public static void main(String[] argvs) throws Exception{
        Sync2 sync = new Sync2();
        for(int i=0; i<2; i++) {
            Thread ta = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        sync.doA();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }, "T" + (i * 2));

            Thread tb = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Sync2.doB();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }, "T" + (i * 2 + 1));

            ta.start();
            tb.start();
        }

    }
}
