package cn.devmgr.javathreads.section6;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public  class StreamTest {

    public static void main(String[] argvs) {
        List<Integer> list = new ArrayList<>();
        for(int i=0; i<1000; i++){
            list.add(i);
        }
        list.stream().forEach(System.out :: println);
        System.out.println("---------------");
        // parallelStream 是并行流，所以打印出来的顺序并不是从0到999的顺序
        list.parallelStream().forEach(System.out :: println);
        System.out.println("---------------");

        Integer[] ints = list.toArray(new Integer[0]);
        // 把 Integer[] 转成 String[]
        String[] s = Stream.of(ints).map(String::valueOf).toArray(String[]::new);
        System.out.println(s.length);

        String[] s2 = Stream.of(ints).map(String::valueOf).toArray(n -> new String[n]);
        System.out.println(s2.length);
    }

    public static  class Ss implements Consumer<Integer>{

        @Override
        public void accept(Integer integer) {
            System.out.println(integer);
        }
    }


}
