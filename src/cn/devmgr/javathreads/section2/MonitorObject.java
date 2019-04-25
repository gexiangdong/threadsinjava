package cn.devmgr.javathreads.section2;

public class MonitorObject {

    private Object obj = new Object();

    public void doA() throws InterruptedException {
        synchronized (obj) {
            System.out.print("1");
            obj.wait();
            System.out.print("2");
        }
    }

    public void doB() throws InterruptedException {
        synchronized (obj) {
            System.out.print("3");
            obj.notify();
            Thread.sleep(500);
            System.out.print("4");
        }
    }

    public static void main(String[] argvs) throws Exception {
        MonitorObject mo = new MonitorObject();
        Thread t1 = new Thread(() -> {
            try {
                mo.doA();
            } catch (InterruptedException ie) {
                throw new RuntimeException(ie);
            }
        }, "T1");

        Thread t2 = new Thread(() -> {
            try {
                mo.doB();
            } catch (InterruptedException ie) {
                throw new RuntimeException(ie);
            }
        }, "T2");

        t1.start();
        Thread.sleep(100);
        t2.start();

    }

}

