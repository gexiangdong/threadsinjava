package cn.devmgr.javathreads.section5;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPool {

    public static void main(String[] argvs) throws Exception{
        ScheduledExecutorService service = Executors.newScheduledThreadPool(5);
        System.out.println("Thread.activeCount() = " + Thread.activeCount());

        for(int i=0; i<10; i++){
            final int index = i;
            Runnable r = () -> {
              System.out.println("start R-" + index);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("InterruptedException R-" + index);
                }
                System.out.println("end R-" + index);
            };

            service.schedule(r, 100 - i * 10 , TimeUnit.MILLISECONDS);
        }

        System.out.println("Thread.activeCount() = " + Thread.activeCount());

        Thread.sleep(60000);
        System.out.println("Thread.activeCount() = " + Thread.activeCount());

        service.shutdown();
        try {
            // service中还有正在运行的任务，等一段时间，让他们执行完毕
            if (!service.awaitTermination(60, TimeUnit.SECONDS)) {
                service.shutdownNow(); // shutdownNow会给正在运行的线程发送Interrupt中断；要求所有线程退出
                // 等待一段时间以便线程处理中断退出
                if (!service.awaitTermination(30, TimeUnit.SECONDS)) {
                    //线程没有在30秒内相应中断并退出
                    System.err.println("无法终止 " + service);
                }
            }
        } catch (InterruptedException ie) {
            // executorservice的主线程收到中断；尝试强制任务线程退出
            service.shutdownNow();
            // 设置中断标识（有InterruptedException不会有中断标识，所以此处再次设置，以便其他地方使用）
            Thread.currentThread().interrupt();
        }
    }
}
