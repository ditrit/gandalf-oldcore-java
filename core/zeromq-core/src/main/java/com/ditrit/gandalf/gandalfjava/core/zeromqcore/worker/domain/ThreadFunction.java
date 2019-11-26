package com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain;

public abstract class ThreadFunction extends Thread {

    public void run() {
        System.out.println("Start");
        while(!this.isInterrupted()) {
            System.out.println("Running");
        }
        System.out.println("Stop");
    }
}
