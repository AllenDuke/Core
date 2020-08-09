package com.github.AllenDuke;

public class CThread {

    public Runnable target;

    public void start(){
        start0();
    }

    private native void start0();

    public void run() {
        target.run();
    }

}
