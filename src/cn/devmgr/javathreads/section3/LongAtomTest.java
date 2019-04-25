package cn.devmgr.javathreads.section3;


/**
 * long 型赋值，在32位JDK上不是原子操作
 * 按照JSR 133，long型、double型都不强制要求jdk采用原子操作，但一般64位jdk采用对这两种类型赋值保持原子性
 */
public class LongAtomTest {
    private static long value = 0;

    public void test(final long v){
        for(long i=0; i<1000000000; i++) {
            value = v;
            long t = value;
            if (t != -1l && t != 1l) {
                System.out.println("错误的值：" + t);
                System.exit(0);
            }
        }
        System.out.println("all done");
    }
    public static void main(String[] argvs) throws Exception{
        String arch = System.getProperty("sun.arch.data.model");
        if("64".equals(arch)){
            System.out.println("在64位JDK下，这看不到long型非原子性赋值");
            return;
        }

        LongAtomTest lat = new LongAtomTest();
        Thread t1 = new Thread(() -> {
            lat.test(-1l);
        }, "T1");
        Thread t2 = new Thread(() -> {
            lat.test(1l);
        }, "T2");

        t1.start();
        t2.start();
    }
}
