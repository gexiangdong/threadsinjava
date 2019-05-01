package cn.devmgr.javathreads.section7;

import java.sql.Connection;
import java.util.concurrent.Semaphore;

public class SampleConnectionPool {
    private static final int MAX = 10;
    private final Semaphore available = new Semaphore(MAX, true);

    protected Connection[] items = new Connection[MAX];
    protected boolean[] used = new boolean[MAX];


    public Connection getOne() throws InterruptedException {
        available.acquire();
        return createOne();
    }
    private synchronized Connection createOne(){
        for (int i = 0; i < MAX; ++i) {
            if (!used[i]) {
                used[i] = true;
                if(items[i] == null){
                   // items[i] = new ...
                }
                return items[i];
            }
        }
        return null;
    }

    public void putOne(Connection conn) {
        if (markAsAvailable(conn)) {
            available.release();
        }
    }
    private synchronized boolean markAsAvailable(Connection conn){
        for (int i = 0; i < MAX; ++i) {
            if (conn == items[i]) {
                if (used[i]) {
                    used[i] = false;
                    return true;
                }else{
                    return false;
                }
            }
        }
        return false;
    }
}
