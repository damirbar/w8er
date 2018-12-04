package com.w8er.android.utils;

import java.util.concurrent.atomic.AtomicInteger;

public class CartID {
    private final static AtomicInteger c = new AtomicInteger(0);
    public static int getID() {
        return c.incrementAndGet();
    }
}
