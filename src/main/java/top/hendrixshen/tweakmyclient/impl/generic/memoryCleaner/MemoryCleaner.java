package top.hendrixshen.tweakmyclient.impl.generic.memoryCleaner;

import top.hendrixshen.tweakmyclient.SharedConstants;

public class MemoryCleaner {
    public static void clean() {
        class CleanerThread implements Runnable {
            @Override
            public void run() {
                SharedConstants.getLogger().info("[{}]: Memory cleaner thread started!", SharedConstants.getModName());
                System.gc();

                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException ignore) {
                }

                System.gc();
                SharedConstants.getLogger().info("[{}]: Memory cleaner thread finished!", SharedConstants.getModName());
            }
        }

        Runnable runnable = new CleanerThread();
        Thread gcThread = new Thread(runnable, "MemoryCleaner GC Thread");
        gcThread.setDaemon(true);
        gcThread.start();
    }
}
