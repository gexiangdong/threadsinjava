package cn.devmgr.javathreads.section7;

import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.PriorityBlockingQueue;

public class TestPriorityBlockingQueue {

    public static void main(String[] argvs) throws  Exception{
        PriorityBlockingQueue<Integer> pbq = new PriorityBlockingQueue<>();
        Random rnd = new Random();
        for(int i=0; i<20; i++){
            pbq.put(rnd.nextInt(50));
        }
        // PriorityBlockingQueue 会排序其中的元素；所以如下输出应该是有序的
        while(pbq.size() > 0){
            System.out.println(pbq.poll());
        }

        System.out.println("----------");

        PriorityBlockingQueue<Integer> pbq2 = new PriorityBlockingQueue<Integer>(10, Comparator.comparing(Object::toString));
        for(int i=0; i<20; i++){
            pbq2.put(i);
        }
        // 这次应该是字符顺序，例如：11在2之前
        while(pbq2.size() > 0){
            System.out.println(pbq2.poll());
        }
    }
}
