package cn.devmgr.javathreads.section5;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class CompletableFutureAndStream {

    public static void main(String[] argvs) throws Exception{
        List<CompletableFuture<String>> tasks = new ArrayList<>();

        for(int i=0; i<10; i++) {
            final int index = i;
            CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
                return "#" + index;
            });

            tasks.add(cf);
        }

        Stream<CompletableFuture<String>> stream = tasks.parallelStream();

        List<String> list = new ArrayList<>();
        stream.map(CompletableFuture::join).forEach((v) -> {
            System.out.println("v " + v.getClass().getName() + "  " + v);
        });

    }
}
