package cn.devmgr.javathreads.section6;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * 用completableFuture获取异步计算结果
 */
public class TestCompletableFuture {

    public static void main(String[] argvs) throws Exception{
        Supplier<String> supplier = () ->{
            System.out.println("starting supplier...");
            try{
                Thread.sleep(5000);
            }catch (Exception e){

            }
            System.out.println("supplier before return");
            return "OK";
        };

        CompletableFuture<String> ct = CompletableFuture.supplyAsync(supplier); // 这句执行后，supplier已经开始异步计算
        Thread.sleep(2000);
        System.out.println("after sleep, try to get result"); //第13行输出会在这个前，因为supplier已经开始异步计算
        System.out.println("result: " + ct.get());
    }
}
