package cn.devmgr.javathreads.section1;

public class GracefulExit implements Runnable {

    @Override
    public void run(){
        while(!Thread.currentThread().isInterrupted()){
            // 线程要处理的任务
            // 如果耗时较长的任务，可在任务中间可退出部分增加 isInterrupted()的判断
            try {
                Thread.sleep(100);
            }catch (InterruptedException e){
                break;
            }
        }
        // 关闭线程打开的资源
        System.out.println("Thread exits.");
    }

    public static void main(String[] argvs) throws InterruptedException{
        Thread t1 = new Thread(new GracefulExit(), "T-1");
        t1.start();

        Thread.sleep(1000);

        // 中断t1线程
        t1.interrupt();
    }
}
