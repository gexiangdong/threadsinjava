package cn.devmgr.javathreads.section1;

/**
 * 这是个负面的例子；正面的请参照GracefulExit
 */
public class SwallowInterruptedException implements Runnable{

    private volatile boolean shouldExit = false;

    public void run(){
        while(!shouldExit){
            //查询并处理

            try{
                Thread.sleep(60000);
            }catch (InterruptedException e){
                // 这样写不好，吞掉了异常，啥也没做
            }
        }
        // 关闭打开的资源，退出线程
    }

    public static void main(String[] argvs) throws Exception{
        SwallowInterruptedException sie = new SwallowInterruptedException();

        Thread t1 = new Thread(sie, "T-1");
        t1.start();
        t1.interrupt();
        // sie.shouldExit = true;
        t1.join();
        System.out.println("exit");
    }

}
