package cn.devmgr.javathreads.section1;

import java.util.ArrayList;
import java.util.List;

public class CreateThread {

    public void doSomething(){
        System.out.println("doSomthing()");
    }
    public static void main(String[] argvs) throws Exception{
        //1. 继承Thread类，来创建线程
        Thread t = new MyThread();
        t.setName("T-1");
        t.start();      //这启动了一个新线程
        t.run();        //没有启动新线程，还是在当前线程内运行

        //2.实现runable，创建线程
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                CreateThread ct = new CreateThread();
                ct.doSomething();
            }
        }, "T-2");
        t2.start();

        //3. 一个runable实例，多个线程
        Runnable runnable = new Runnable() {
            private List<String> list = new ArrayList<>();

            @Override
            public void run() {
                for(int i=0; i<3; i++) {
                    synchronized (this) {
                        list.add(Thread.currentThread().getName() + "-" + i);
                    }
                }
                System.out.println(String.join(",", list));
            }

        };
        new Thread(runnable, "A").start();
        new Thread(runnable, "B").start();
        new Thread(runnable, "C").start();
    }



    public static class MyThread extends Thread{

        @Override
        public void run() {
            System.out.println("in MyThread, " + Thread.currentThread().getName());
            try {
                sleep(1);
            }catch (InterruptedException e){

            }
            System.out.println("end of MyThread. " + Thread.currentThread().getName());
        }
    }

}
