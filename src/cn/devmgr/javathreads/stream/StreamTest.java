package cn.devmgr.javathreads.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public  class StreamTest {

    public static void main(String[] argvs) {
        List<Integer> list = new ArrayList<>();
        for(int i=0; i<1000; i++){
            list.add(i);
        }
        list.stream().forEach(System.out :: println);
        System.out.println("---------------");
        list.parallelStream().forEach(System.out :: println);
        System.out.println("---------------");
        list.stream().parallel().forEach(System.out :: println);
        list.stream().parallel().forEach((Integer x) -> {System.out.println(x);});
        list.stream().parallel().forEach(new Ss());


    }

    public static  class Ss implements Consumer<Integer>{

        @Override
        public void accept(Integer integer) {
            System.out.println(integer);
        }
    }


}
