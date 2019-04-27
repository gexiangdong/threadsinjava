package cn.devmgr.javathreads.section5;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CachedThreadPoolTest {


    public static void main(String[] argvs) throws Exception{
        ExecutorService service = Executors.newCachedThreadPool();
        System.out.println(service.getClass().getName());
        System.out.println("activeCount(): " + Thread.activeCount());
        for(int i=0; i<50; i++){
            final int index = i;
            Runnable r = () ->{
                System.out.println("[" + Thread.currentThread().getName() + "; " + index + "] running");
                try {
                    Thread.sleep(100 * index);
                }catch (InterruptedException e){
                    System.out.println("InterruptedException #" + index);
                }
                System.out.println("["+ Thread.currentThread().getName() + "; " +  + index + "] will exit");
            };
            Thread.sleep(100);
            service.submit(r);
        }

        Thread.sleep(10000);
        System.out.println("activeCount(): " + Thread.activeCount());
        service.shutdown();
        try {
            int tryCounter = 0;
            while (!service.awaitTermination(1, TimeUnit.SECONDS)) {
                tryCounter++;
                if(tryCounter > 1){
                    System.err.println("无法终止 executorService");
                    break;
                }
                service.shutdownNow();
            }
        } catch (InterruptedException ie) {
            service.shutdownNow();
            Thread.currentThread().interrupt();
        };
    }
}
