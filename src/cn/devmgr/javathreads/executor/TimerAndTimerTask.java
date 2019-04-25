package cn.devmgr.javathreads.executor;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class TimerAndTimerTask {

    public static void main(String[] argvs) throws Exception {
        usegTimer();
        System.out.println("\r\n-----------\r\n");
        usePool();
    }

    private static void printThreads(){
        Thread[] tary = new Thread[Thread.activeCount()];
        Thread.enumerate(tary);
        for(int i=0; i<tary.length; i++){
            System.out.println(" #" + i + " " + tary[i].getName() + "  " + tary[i].getId() + " " + tary[i].isDaemon());
        }
    }
    public static void usePool() throws Exception{
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        System.out.println("Thread.activeCount() = " + Thread.activeCount());
        printThreads();

        ScheduledFuture<?>[] sfs = new  ScheduledFuture<?>[5];
        for(int i=0; i<5; i++) {
            final int index = i;
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    System.out.println("runnable task #" + index);
                    if (index == 3) {
                        throw new RuntimeException("throws Exception");
                    }
                }
            };

            sfs[i] = service.schedule(task, 1000 * (i + 1), TimeUnit.MILLISECONDS);
        }
        System.out.println("Thread.activeCount() = " + Thread.activeCount());

        Thread.sleep(10000);
        System.out.println("Thread.activeCount() = " + Thread.activeCount());
        for(int i=0; i<5; i++){
            System.out.println(sfs[i]);
            // System.out.println(sfs[i].get()); 如果runnable里抛出异常，在调用对应的future的get()时，会抛出那个异常；
        }
        service.shutdown();
        service.shutdownNow();

    }
    public static void usegTimer() throws Exception{
        Timer timer = new Timer("Timer", true);
        System.out.println("Thread.activeCount() = " + Thread.activeCount());
        printThreads();

        for(int i=0; i<5; i++){
            final int index = i;
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    System.out.println("timer task #" + index);
                    if(index == 3){
                        throw new RuntimeException("throws Exception");
                    }
                    try{
                        Thread.sleep(500);
                    }catch(InterruptedException e){

                    }
                }
            };

            timer.schedule(task, 1000 * (i + 1));
        }

        System.out.println("Thread.activeCount() = " + Thread.activeCount());

        Thread.sleep(10000);
        System.out.println("Thread.activeCount() = " + Thread.activeCount());

    }
}
