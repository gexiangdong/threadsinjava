package cn.devmgr.javathreads.section3;

import java.util.HashMap;

public class VolatileOnReferenceObject {

    private HashMap<String, Boolean> map = new HashMap<>();

    public void waitForExit(){
        System.out.println("[" + Thread.currentThread().getName() + "] waitingForExit()");
        while(map.get("exit") == null){
        }
        System.out.println("[" + Thread.currentThread().getName() + "] waitForExit end");
    }

    public static void main(String[] argvs) throws Exception{
        final VolatileOnReferenceObject voro = new VolatileOnReferenceObject();
        for(int i=0; i<2; i++){
            new Thread(new Runnable(){

                @Override
                public void run() {
                    voro.waitForExit();
                }
            }, "T-" + i).start();
        }
        HashMap<String, Boolean> newMap = new HashMap<>();
        newMap.put("exit", true);
        voro.map = newMap;
        //voro.mic.shouldExit = true;
        //voro.mic = voro.new MyInnerClass(true);
//        voro.mic.shouldExit = true;

    }


    public class MyInnerClass{
        public boolean shouldExit = false;
        public MyInnerClass(boolean b){
            shouldExit = b;
        }
    }
}
