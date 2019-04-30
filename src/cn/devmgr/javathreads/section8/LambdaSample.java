package cn.devmgr.javathreads.section8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class LambdaSample {

    public static void main(String[] argvs) throws Exception{
        MyFunction[] mfs = new MyFunction[5];
        mfs[0] = (int x, int y) -> {return x + y;};
        mfs[1] = (int x, int y) -> x + y;
        mfs[2] = (x, y) -> x + y;
        mfs[3] = new MyFunction() {
            @Override
            public int cal(int x, int y) {
                return x + y;
            }
        };
        mfs[4] = (x, y) -> x;
        for (MyFunction mf : mfs) {
            System.out.println(mf.cal(2, 3));
        }

        System.out.println("\r\n---------\r\neta conversion\r\n");
        List<Integer> list = new ArrayList<>();
        list.add(4);
        list.add(8);
        list.forEach(System.out::println);

    }

    public static interface MyFunction{
        int cal(int x, int y);

        default int add(int x, int y){
            return x + y;
        }

        static int multiple(int x, int y){
            return x * y;
        }
    }


}
