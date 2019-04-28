package cn.devmgr.javathreads.section7;

import java.util.concurrent.Semaphore;

public class SemaphoreTest {

    public static void main(String[] argvs) throws Exception{
        Semaphore s = new Semaphore(-5);
        System.out.println(s.availablePermits()); // 5
        Thread t1 = new Thread(() -> {
            try{
                s.acquire(5);
                s.acquire(5);
                Thread.sleep(5000);
            }catch(Exception e){

            }});
        t1.start();
        Thread.sleep(200);
        System.out.println(s.availablePermits()); // 5
        System.out.println("drainPermits: " + s.drainPermits());
        System.out.println(s.availablePermits()); // 0
        s.drainPermits();
        System.out.println(s.availablePermits()); // 0

        s.release(5);
        System.out.println(s.availablePermits()); // 0

    }
}
