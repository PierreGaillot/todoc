package com.cleanup.todoc;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {

    private static Executor INSTANCE = Executors.newSingleThreadExecutor();

    public static void setExecutor(Executor executor){
        INSTANCE = executor;
    }

    public static Executor getInstance() {
        return INSTANCE;
    }
}
