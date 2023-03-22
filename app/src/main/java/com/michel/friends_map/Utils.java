package com.michel.friends_map;

public class Utils {

    private Object lock;

    public Utils(){
        lock = new Object();
    }

    public void lockWait() {
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void lockNotify(){
        synchronized (lock){
            lock.notify();
        }
    }
}
