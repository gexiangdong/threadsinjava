package cn.devmgr.javathreads.section3;

/**
 * 指令重排的例子
 *
 * 此例很难遇到打印a != 1并退出；需要1：调整合适的等待时间；2：多次执行； 3：运气
 *
 * 此例是可见性造成的较低，因为两个变量a, flag都是在method1处修改，而对method2一个可见，一个不可见,概率较低
 *
 */
public class BadlyOrdered {
    private int a = 2;
    private boolean flag = false;

    public void method1() {
        //这个赋值语句在CPU中大约只占几纳秒的时间，甚至更少，即使是先执行flag=true，也很难遇到flag=true,a=2的状况
        a = 1;
        flag = true;
    }

    public void method2() {
        if (flag) {
            // 由于指令重排，此时可能 a == 2
            if(a != 1){
                System.out.println("遇到a != 1 ");
                System.exit(0);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        long i = 0;
        while(true){
            i++;
            BadlyOrdered reorderExample = new BadlyOrdered();
            Thread t1 = new Thread(() -> {
                reorderExample.method1();
            });
            Thread t2 = new Thread(() -> {
                reorderExample.method2();
            });
            t1.start();
            t2.start();
            t1.join();
            t2.join();
            if(i % 100000 == 0){
                System.out.println("第" + (i / 10000) + "万次");
            }
        }
    }
}
