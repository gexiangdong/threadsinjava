package cn.devmgr.javathreads.section2;

public class Sync3 {

    public synchronized void doA() throws InterruptedException{
        System.out.println("[" + Thread.currentThread().getName() + "]<begin doA");
        Thread.currentThread().sleep(500);
        System.out.println("[" + Thread.currentThread().getName() + "]\t\tend doA>");
    }

    public synchronized static void doB() throws InterruptedException{
        System.out.println("[" + Thread.currentThread().getName() + "]<begin doB");
        Thread.currentThread().sleep(500);
        System.out.println("[" + Thread.currentThread().getName() + "]\t\tend doB>");
    }

    public static void main(String[] argvs) throws Exception{
        Sync3 sync = new Sync3();
        for(int i=0; i<1; i++) {
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
                        Sync3.doB();
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
