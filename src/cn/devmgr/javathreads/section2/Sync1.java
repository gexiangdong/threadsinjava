package cn.devmgr.javathreads.section2;

public class Sync1 {

    public void doA() throws InterruptedException{
        synchronized (this) {
            System.out.println("[" + Thread.currentThread().getName() + "]<begin doA");
            Thread.currentThread().sleep(500);
            System.out.println("[" + Thread.currentThread().getName() + "]\t\tend doA>");
        }
    }

    public synchronized void doB() throws InterruptedException{
        System.out.println("[" + Thread.currentThread().getName() + "]<begin doB");
        Thread.currentThread().sleep(500);
        System.out.println("[" + Thread.currentThread().getName() + "]\t\tend doB>");
    }

    public static void main(String[] argvs) throws Exception{
        Sync1 sync = new Sync1();
        for(int i=0; i<2; i++) {
            Thread ta = new Thread(() -> {
                try {
                    sync.doA();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }, "T" + (i * 2));

            Thread tb = new Thread(() -> {
                try {
                    sync.doB();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }, "T" + (i * 2 + 1));

            ta.start();
            tb.start();
        }

    }
}
