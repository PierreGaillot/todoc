package com.cleanup.todoc;

import java.util.concurrent.Executor;

public class UnitTestExecutor implements Executor {
    public void execute(Runnable r) {
        r.run();
    }
}
