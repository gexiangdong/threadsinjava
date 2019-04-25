package cn.devmgr.javathreads.section3;

/**
 * JVM在编译或执行期间指令会重新排序的证明
 * <p>
 * a b x y初始值都是0
 * 两个线程
 * I： a = 1; x = b;
 * II:  b = 1; y = a;
 * <p>
 * 如果没有指令重排，那么两个线程都执行完毕，xy的可能值对是：
 * I先执行完，后II，(0,1)
 * II先执行完，然后I， (1,0)
 * I执行完a=1，然后II执行b=1;y=a，然后I执行x=b, (1,1)
 * I执行完a=1, 然后II执行b=1;I执行x=b；II执行y=a, (1,1)
 * II先执行b=1,然后I执行b=1, x=b; II执行y=a, (1,1)
 * II先执行b=1,然后I执行b=1, II执行y=a；I执行x=b (1,1)
 *
 * 只可能就6种情况，这6种情况没有（0,0）。由于Java在编译或执行期间可能对程序进行优化，会调整指令的执行顺序
 * 出现（0，0）是可能的。
 * 因此多次执行此程序会有(0,0)出现
 * 这符合JSR 133种的规定
 *
 * ****** 这个例子也可能是可见性造成的，而且我根绝可见性的远影更高 ********
 * t1 对a的修改，t2不可见；所以t2用了0；同样t2修改的b，t1也不可见
 * 此例中把a和b设置成volatile设置成volatile可保证不是可见性造成的，但是这样后却又禁止重排。这不是一个好例子；
 *
 * BadlyOrdered 的例子是可见性造成的可能性很低
 *
 */
public class PossibleReordering {
    private static int x = 0, y = 0;
    private static int a = 0, b = 0;

    public static void main(String[] args) throws InterruptedException {
        long i = 0;

        while (true) {
            i++;
            x = 0;
            y = 0;
            a = 0;
            b = 0;
            Thread t1 = new Thread(() -> {
                long start = System.nanoTime();
                long end;
                do {
                    //稍等会后启动的线程，这里是纳秒，1,000,000纳秒=1毫秒；
                    end = System.nanoTime();
                } while ((start + 80000) >= end);

                a = 1;
                x = b;
            });

            Thread t2 = new Thread(() -> {
                b = 1;
                y = a;
            });

            t1.start();
            t2.start();
            t1.join();
            t2.join();

            if (x == 0 && y == 0) {
                System.out.println("在第" + i + "次时遇到了(" + x + "," + y + ")；");
                break;
            } else {
                if (i % 10000 == 0) {
                    System.out.println("已执行了" + (i / 10000) + "万次");
                }
            }
        }
    }
}

