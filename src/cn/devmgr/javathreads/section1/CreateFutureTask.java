package cn.devmgr.javathreads.section1;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class CreateFutureTask {


    public static void main(String[] argvs) throws Exception{
        Callable<String> callable = () -> {
            System.out.println("in callable");
            Thread.sleep(1000);
            System.out.println("in callable after sleep");
//            throw new RuntimeException("hhhh");
            return "hello";
        };

        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<String> f =  executorService.submit(callable);
        Thread.sleep(3000);
        // 可以执行其他代码，callable会自动开始计算并将结果保存到Future内
        try {
            String result = f.get(); //如果callable的call方法尚未运算完毕，会等待运算完毕
            System.out.println("result: " + result);
        }catch(Exception e){
            System.out.println("catch error");
            e.printStackTrace();
        }

        executorService.shutdown();
        try {
            int tryCounter = 0;
            while (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                tryCounter++;
                if(tryCounter > 2){
                    System.err.println("无法终止 executorService");
                    break;
                }
                executorService.shutdownNow();
            }
        } catch (InterruptedException ie) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        };
    }
}
