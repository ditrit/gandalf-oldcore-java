package com.orness.core.testgrpcservice.entity;

import com.google.common.base.Preconditions;

import java.util.concurrent.CancellationException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class MessageFuture implements Future<String> {
    private final String message;
    private boolean canceled;
    private boolean done;

    public MessageFuture(String message) {
        Preconditions.checkNotNull(message, "histogram");
        this.message = message;
    }

    @Override
    public synchronized boolean cancel(boolean mayInterruptIfRunning) {
        if (!done && !canceled) {
            canceled = true;
            notifyAll();
            return true;
        }
        return false;
    }

    @Override
    public synchronized boolean isCancelled() {
        return canceled;
    }

    @Override
    public synchronized boolean isDone() {
        return done || canceled;
    }

    @Override
    public synchronized String get() throws InterruptedException {
        while (!isDone() && !isCancelled()) {
            wait();
        }

        if (isCancelled()) {
            throw new CancellationException();
        }

        return message;
    }

    @Override
    public String get(long timeout, TimeUnit unit) {
        throw new UnsupportedOperationException();
    }

    public synchronized void done() {
        done = true;
        notifyAll();
    }
}