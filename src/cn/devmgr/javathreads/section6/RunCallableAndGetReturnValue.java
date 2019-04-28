package cn.devmgr.javathreads.section6;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * 两个运行Callable的方法
 */
public class RunCallableAndGetReturnValue {

    public static void main(String[] argvs) throws Exception {
        runUsingExecutorService();
        System.out.println("--------");
        runUsingThread();
    }

    private static Callable<String> createCallable(){
        Callable<String> callable = () -> {
            System.out.println("starting callable...");
            Thread.sleep(1000);
            System.out.println("callable before return");
            return "OK";
        };
        return callable;
    }

    /**
     * 使用ExecutorService运行Callable
     * @throws Exception
     */
    public static void runUsingExecutorService() throws Exception{
        Callable<String> callable = createCallable();

        ExecutorService threadPool = Executors.newCachedThreadPool();
        Future<String> future = threadPool.submit(callable); // 提交后由 ExecutorService 安排异步运行；cached thread pool会立即运行。
        Thread.sleep(200);
        System.out.println("result: " + future.get());

        threadPool.shutdown();
        try {
            if (!threadPool.awaitTermination(1, TimeUnit.MINUTES)) {
                threadPool.shutdownNow();
                if (!threadPool.awaitTermination(20, TimeUnit.SECONDS)) {
                    System.err.println("无法终止 " + threadPool);
                }
            }
        } catch (InterruptedException ie) {
            threadPool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    /**
     * 使用Thread运行Callable
     * @throws Exception
     */
    public static void runUsingThread() throws Exception{
        Callable<String> callable = createCallable();

        FutureTask<String> ft = new FutureTask(callable);
        Thread t = new Thread(ft);
        t.start();
        Thread.sleep(200);
        System.out.println("result: " + ft.get());
    }
}
