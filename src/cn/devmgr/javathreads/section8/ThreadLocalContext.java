package cn.devmgr.javathreads.section8;

import java.util.HashMap;
import java.util.Map;

public class ThreadLocalContext {

    protected ThreadBindings bindings = new ThreadBindings();

    private static ThreadLocalContext instance = new ThreadLocalContext();

    protected class ThreadBindings extends ThreadLocal<Map<String, Object>> {
        public Map<String, Object> initialValue() {
            return new HashMap<String, Object>();
        }

        public Map<String, Object> getBindings() {
            return (Map<String, Object>) get();
        }
    }

    //确保单例模式
    private ThreadLocalContext(){
    }

    public static ThreadLocalContext getInstance(){
        return instance;
    }

    public static void setValue(String name, Object value){
        instance.set(name, value);
    }

    public static Object getValue(String name){
        return instance.get(name);
    }

    public void set(String name, Object value) {
        // find the map that might already exist
        Map<String, Object> bindings = this.bindings.getBindings();
        if (bindings == null) {
            return;
        }

        if (value == null) {
            bindings.remove(name);
        }else {
            bindings.put(name, value);
        }
    }

    public void clear(){
        Map<?, ?> bindings = this.bindings.getBindings();
        if (bindings == null) {
            return;
        }
        bindings.clear();
        this.bindings.remove();
    }

    public Object get(String name) {
        Map<?, ?> bindings = this.bindings.getBindings();
        if (bindings == null) {
            return null;
        }
        return bindings.get(name);
    }
}
