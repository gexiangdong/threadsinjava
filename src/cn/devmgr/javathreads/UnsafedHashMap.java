package cn.devmgr.javathreads;

import java.util.HashMap;
import java.util.Map;

/**
 * HashMap多线程并发添加条目（put）时，可能会出现：
 * 1、 java.lang.ClassCastException: java.util.HashMap$Node cannot be cast to java.util.HashMap$TreeNode
 * 2、 最后的map.size() 不等于添加的数目（threads.length) 有数据丢失
 *
 * 用ConcurrentHashMap 可以解决上述两个问题
 */
public class UnsafedHashMap {

    public static void main(String[] argvs) throws Exception {
//        Map<String, Long> map = new ConcurrentHashMap<>();    //不会出问题
        Map<String, Long> map = new HashMap<>();            //多线程并发可能出问题：1、丢数据；2：put时抛出异常

        Thread[] threads = new Thread[10000];
        for(int i=0; i<threads.length; i++){
            final String key = "key-" + i;
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    map.put(key, new Long(System.currentTimeMillis()));
                }
            });
        }
        for(int i=0; i<threads.length; i++) {
            threads[i].start();
        }

        //等待所有线程都执行完
        for(int i=0; i<threads.length; i++) {
            threads[i].join();
        }
        System.out.println(map.size());
        for(String k : map.keySet()){
            //System.out.println(k);
        }
    }
}
