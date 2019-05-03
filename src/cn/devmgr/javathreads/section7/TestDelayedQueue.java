package cn.devmgr.javathreads.section7;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class TestDelayedQueue {

    public static void main(String[] argvs) throws  Exception{
        DelayedObject do1 = new DelayedObject(100);
        DelayedObject do2 = new DelayedObject(10);
        DelayedObject do3 = new DelayedObject(50);

        DelayQueue<DelayedObject> dq = new DelayQueue<>();
        dq.put(do1);
        dq.put(do2);
        dq.put(do3);
        System.out.println("after put.");
        System.out.println(dq.take());  //会获得 do2 （需要等do2的延时到期后才能得到）
        System.out.println(dq.take());  //会得到 do3
        System.out.println(dq.take());  //会得到 do1
    }


    public static class DelayedObject implements Delayed {
        private long timeoutTime;
        private long delayedMs;

        public DelayedObject(long delayedMs) {
            this.delayedMs = delayedMs;
            this.timeoutTime = System.currentTimeMillis() + delayedMs;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            long ms = this.timeoutTime - System.currentTimeMillis();
            System.out.println("getDelay return " + ms);
            return unit.convert(ms, TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            Long l1 = this.timeoutTime - System.currentTimeMillis();;
            Long l2 = o.getDelay(TimeUnit.MILLISECONDS);
            System.out.println("compareTo  " + l1 + " with " + l2);
            return l1.compareTo(l2);
        }

        @Override
        public String toString() {
            return "DelayedObject[" + delayedMs + "]";
        }

    }
}
