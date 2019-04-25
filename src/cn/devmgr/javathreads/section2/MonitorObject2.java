package cn.devmgr.javathreads.section2;

public class MonitorObject2 {

    private Object obj = new Object();

    public void doA() throws InterruptedException{
        synchronized(obj){
            System.out.print("1");
            obj.wait(1000);
            System.out.print("2");
        }
    }

    public  void doB() throws InterruptedException{
        synchronized(obj){
            System.out.print("3");
            obj.notify();
//            Thread.currentThread().sleep(2000);
            System.out.print("4");
        }
    }

    public static void main(String[] argvs) throws Exception{
        MonitorObject2 mo = new MonitorObject2();
        Thread t1 = new Thread(() ->{
            try{
                mo.doA();
            }catch (Exception e){

            }
        }, "T1");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.currentThread().sleep(100);
                    mo.doB();
                }catch(InterruptedException ie){
                    // 啥也不做，或者仅仅记录个日志 throw new RuntimeException(ie);
                }
            }
        }, "T2");

        t1.start();
        t2.start();

    }

}
