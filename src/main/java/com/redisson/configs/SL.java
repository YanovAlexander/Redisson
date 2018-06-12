package com.redisson.configs;

import com.google.inject.Injector;

public class SL {
    private static Injector injector = null;
    private static SL soleInstance = null;
    private static Object lockObject = new Object();

    public static <T> T get(Class<T> aClass) {
        return injector.getInstance(aClass);
    }

    public static void load(SL locator) {
        synchronized (lockObject) {
            soleInstance = locator;
        }
    }

    public static void load(Injector injector) {
        synchronized (lockObject) {
            SL.injector = injector;
        }
    }

    public static SL getInstance() {
        return soleInstance;
    }

    public static void destroy() {
        synchronized (lockObject) {
            SL.injector = null;
        }
    }
}

