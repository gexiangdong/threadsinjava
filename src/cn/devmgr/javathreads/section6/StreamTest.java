package cn.devmgr.javathreads.section6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public  class StreamTest {

    public static void main(String[] argvs) {
        System.out.println("---------------");
        parallel();
        System.out.println("\r\n-------map--------");
        map();
        System.out.println("\n-----flatMap----------");
        flatMap();
        System.out.println("\r\n----reduce-----------");
        reduce();
        System.out.println("\r\n----collect-----------");
        collect();
    }
    private static void parallel() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            list.add(i);
        }
        // 串行流打印出来的是0-999的顺序
        list.stream().forEach(System.out::println);
        System.out.println("---------------");
        // parallelStream 是并行流，所以打印出来的顺序并不是从0到999的顺序
        list.parallelStream().forEach(System.out::println);
    }

    private static void map() {
        Stream<String> stream = Stream.of("hello", "world");
        // map是一对一，结果是{["h","e","l","l","0"], ["w","o","r","l","d"]}
        stream.map(s -> s.split("")).forEach(System.out::println);
    }
    private static void flatMap() {
        Stream<String> stream = Stream.of("hello", "world");
        // flatMap是一对多，正则表达式返回的是Stream, 每个Stream会都合并到一起，
        // 最终结果是["h","e","l","l","0", "w","o","r","l","d"]
        stream.flatMap(s -> Arrays.stream(s.split(""))).forEach(System.out::println);
    }

    private static void reduce(){
        // 字符串连接，concat = "ABCD"
        String s1 = Stream.of("A", "B", "C", "D").reduce("letters：", String::concat);
        System.out.println(s1);

        String s2 = Stream.of("A", "B", "C", "D").reduce("字母：", (sa, sb) -> {System.out.println(sa + "-" + sb); return sa + sb + ",";});
        System.out.println("最终结果" + s2);
    }

    private static void collect(){
        Stream<String> stream = Stream.of("a", "b", "c", "d");
        // Collectors是个工具类，提供很多生成 Collector的静态方法
        List<Character> list = stream.map(s -> s.charAt(0)).collect(Collectors.toList());
    }

}
